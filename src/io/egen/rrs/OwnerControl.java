package io.egen.rrs;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import io.egen.beans.OwnerBean;
import io.egen.beans.ProfileBean;
import io.egen.beans.ReservationBean;
import io.egen.beans.ResponseBean;
import io.egen.beans.TableBean;
import io.egen.dao.owner.ContactListDAO;
import io.egen.dao.owner.LoginDAO;
import io.egen.dao.owner.ProfileDAO;
import io.egen.dao.owner.ReservationListDAO;
import io.egen.dao.owner.SeatingDAO;
import io.egen.dao.owner.TableDAO;
import io.egen.utils.DAOException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@Path("owner")
@Api(tags = { "owner" })
public class OwnerControl {

	/**
	 * Login of Owner
	 */
	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Login", notes = "Login of the Owner")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Service Error") })
	public OwnerBean login(@QueryParam("username") String username, @QueryParam("password") String password) {
		try {
			return new LoginDAO().login(username, password);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * View list of Reservations
	 */
	@Path("listReservations/{date}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View Reservation", notes = "View Reservation List by the Owner for a particular date. "
			+ "Date MM-dd-yyyy eg:01-01-2016")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Service Error") })
	public List<ReservationBean> listReservations(@PathParam("date") String date) {
		try {
			return new ReservationListDAO().generateReservationList(date);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * View profile of the restaurant
	 */
	@Path("getProfile")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View profile", notes = "View profile of the restaurant by the Owner")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Service Error") })
	public ProfileBean getProfile() {
		try {
			return new ProfileDAO().getProfileDetails();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Edit profile of the restaurant
	 */
	@Path("editProfile")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Edit profile", notes = "Edit profile of the restaurant by the Owner")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Service Error") })
	public ResponseBean editProfile(@QueryParam("name") String name, @QueryParam("contact") String contact,
			@QueryParam("email") String email, @QueryParam("address") String address,
			@QueryParam("autoAssign") int autoAssign, @QueryParam("opening") String opening,
			@QueryParam("closing") String closing, @QueryParam("openDays") String openDays) {
		try {
			ProfileBean profileBean = new ProfileBean(name, contact, email, address, autoAssign, opening, closing,
					openDays);
			new ProfileDAO().updateProfileDetails(profileBean);
			return new ResponseBean("Success");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return new ResponseBean("Failure");
	}
	
	/**
	 * View contact lists of the customers
	 */
	@Path("contactList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View contactList", notes = "View contactList of the customers by the Owner")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Service Error") })
	public List<String> contactList() {
		try {
			return new ContactListDAO().getContactList();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * View reservation lists of the a particular customer
	 */
	@Path("reservationsForContact/{phonenumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View reservation for Contact", notes = "View list of resservations of a particular customer from his contact number by the Owner")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Service Error") })
	public List<ReservationBean> contactList(@PathParam("phonenumber") String phonenumber) {
		try {
			List<ReservationBean> reservationList = new ReservationListDAO()
					.generateReservationListforContacts(phonenumber);
			return reservationList;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * View list of table details by the owner
	 */
	@Path("tableList/{date}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View table details", notes = "View list od table details by the owner. "
			+ "Date MM-dd-yyyy eg:01-01-2016")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Service Error") })
	public List<TableBean> tableList(@PathParam("date") String date) {
		try {
			List<TableBean> tableList = new TableDAO().generateTableLists(date);
			return tableList;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Assign or Change Table by the Owner
	 */
	@Path("assignTable")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Assign/Change Table", notes = "Assign or change table by the Owner")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Service Error") })
	public ResponseBean assignTable(@QueryParam("confirmationCode") String confirmationCode,
			@QueryParam("tableName") String tableName) {
		try {
			new SeatingDAO().updateTable(confirmationCode, tableName);
			return new ResponseBean("Success");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return new ResponseBean("Failure");
	}
}