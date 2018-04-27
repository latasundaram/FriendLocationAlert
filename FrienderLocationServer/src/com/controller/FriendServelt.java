package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import android.R.array;
import android.database.sqlite.SQLiteConstraintException;

import com.model.ConnectionUtil;

/**
 * Servlet implementation class FriendServelt
 */
public class FriendServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	PrintWriter out = null;
	JSONArray dataArray;
	ArrayList<String> myFriends;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FriendServelt() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String usecase = request.getParameter("usecase");
		System.out.println("value is===========================" + usecase);
		if (usecase.equalsIgnoreCase("registration")) {
			userRegistration(request, response);
		} else if (usecase.equalsIgnoreCase("login")) {
			userLogin(request, response);
		} else if (usecase.equalsIgnoreCase("question")) {
			getQuestion(request, response);
		} else if (usecase.equalsIgnoreCase("forgotpassword")) {
			forgotpasssword(request, response);
		} else if (usecase.equalsIgnoreCase("setstatus")) {
			setstatus(request, response);
		} else if (usecase.equalsIgnoreCase("checkfriends")) {
			System.out.println("check********************************");
			checkFriends(request, response);
		} else if (usecase.equalsIgnoreCase("accecpt")) {
			accecptFriends(request, response);
		} else if (usecase.equalsIgnoreCase("reject")) {
			rejectFriends(request, response);
		} else if (usecase.equalsIgnoreCase("flist")) {
			Friends(request, response);
		} else if (usecase.equalsIgnoreCase("getprofile")) {
			getProfile(request, response);
		} else if (usecase.equalsIgnoreCase("update")) {
			updateProfile(request, response);
		} else if (usecase.equalsIgnoreCase("getlocation")) {
			getLocation(request, response);
		} else {

			System.out.println("action is not found");
		}
	}

	private void getLocation(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String mobile_no = request.getParameter("mob");
		con = ConnectionUtil.getConnection();
		try {
			st = con.createStatement();
			out = response.getWriter();
			String query = "UPDATE USER_DETAILS SET LATTITUDE='" + latitude
					+ "',LONGTUDE='" + longitude + "' WHERE MOBILE_NUMBER="
					+ mobile_no;
			System.out.println(query);
			int i = st.executeUpdate(query);
			if (i > 0) {

				out.print("success");
			} else {
				out.print("fail");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st);
			outClose(out);

		}

	}

	private void updateProfile(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		String answer = request.getParameter("answer");
		String qid = request.getParameter("qid");
		String radius = request.getParameter("radius");
		String pwd = request.getParameter("pwd");
		String id = request.getParameter("id");
		String dob = request.getParameter("dob");
		con = ConnectionUtil.getConnection();
		try {
			st = con.createStatement();
			out = response.getWriter();
			String query = "UPDATE USER_DETAILS SET NAME='" + name + "', ID='"
					+ id + "'" + ", PASSWORD='" + pwd + "', DATE_OF_BIRTH='"
					+ dob + "', RADIUS='" + radius + "', QUES_ID='" + qid
					+ "', SECURITY_ANS='" + answer
					+ "'       WHERE MOBILE_NUMBER=" + number;
			System.out.println(query);
			int i = st.executeUpdate(query);
			if (i > 0) {

				out.print("success");
			} else {
				out.print("fail");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st);
			outClose(out);

		}

	}

	private void getProfile(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userid = request.getParameter("userid");
		con = ConnectionUtil.getConnection();
		try {
			st = con.createStatement();
			out = response.getWriter();
			this.st = con.createStatement();
			String query1 = "SELECT U.NAME,U.PASSWORD,U.ID,U.MOBILE_NUMBER,Q.QUESTION,U.SECURITY_ANS,U.RADIUS,U.DATE_OF_BIRTH FROM USER_DETAILS U,SECURITY_QUES Q WHERE MOBILE_NUMBER="
					+ userid + " AND U.QUES_ID=Q.QUES_ID";
			System.out.println(query1);
			rs = st.executeQuery(query1);
			if (rs.next()) {

				JSONObject object = new JSONObject();
				object.put("name", rs.getString(1));
				object.put("password", rs.getString(2));
				object.put("id", rs.getString(3));
				object.put("number", rs.getString(4));
				object.put("qname", rs.getString(5));
				object.put("answer", rs.getString(6));
				object.put("radius", rs.getString(7));
				object.put("dob", rs.getString(8));
				out.print(object);

			} else {
				out.print("empty");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.print("add");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st, rs);
			outClose(out);

		}

	}

	private void Friends(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userid = request.getParameter("userid");
		con = ConnectionUtil.getConnection();
		try {
			dataArray = new JSONArray();
			st = con.createStatement();
			out = response.getWriter();
			this.st = con.createStatement();
			String query1 = "SELECT A.NAME,A.MOBILE_NUMBER,B.STATUS,A.LATTITUDE,A.LONGTUDE FROM (SELECT NAME,MOBILE_NUMBER,LATTITUDE,LONGTUDE FROM USER_DETAILS WHERE MOBILE_NUMBER IN(SELECT FMOBILE_NUMBER FROM FRIEND_LIST WHERE MOBILE_NUMBER="
					+ userid
					+ ")) A,FRIEND_LIST B WHERE A.MOBILE_NUMBER=B.FMOBILE_NUMBER AND B.MOBILE_NUMBER="
					+ userid;
			System.out.println(query1);
			rs = st.executeQuery(query1);
			if (rs.next()) {
				do {
					JSONObject object = new JSONObject();
					object.put("name", rs.getString(1));
					object.put("number", rs.getString(2));
					object.put("status", rs.getString(3));
					object.put("lat", rs.getString(4));
					object.put("lon", rs.getString(5));
					dataArray.add(object);

				} while (rs.next());
				out.print(dataArray);
			} else {
				out.print("empty");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.print("add");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st, rs);
			outClose(out);

		}

	}

	private void rejectFriends(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		// String status = request.getParameter("status");
		String number = request.getParameter("number");
		String fnumber = request.getParameter("fnumber");
		con = ConnectionUtil.getConnection();
		try {
			st = con.createStatement();
			out = response.getWriter();
			String query = "DELETE FROM FRIEND_LIST WHERE MOBILE_NUMBER="
					+ number + " AND FMOBILE_NUMBER=" + fnumber;
			System.out.println(query);
			int i = st.executeUpdate(query);
			if (i > 0) {

				out.print("success");
			} else {
				out.print("fail");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st);
			outClose(out);

		}

	}

	private void accecptFriends(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String status = request.getParameter("status");
		String userid = request.getParameter("userid");
		String fnumber = request.getParameter("fnumber");
		con = ConnectionUtil.getConnection();
		try {
			st = con.createStatement();
			out = response.getWriter();
			this.st = con.createStatement();
			String query1 = "INSERT INTO FRIEND_LIST VALUES(" + userid + ","
					+ fnumber + ",'off')";
			System.out.println(query1);
			int j = st.executeUpdate(query1);
			if (j > 0) {
				System.out.println("success");
				out.print("success");

			}// if
			else {
				out.print("fail");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.print("add");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st);
			outClose(out);

		}

	}

	private void checkFriends(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("check********************************1");
		myFriends = new ArrayList<String>();
		String contacts = request.getParameter("contacts");
		System.out.println(contacts);
		System.out.println("check********************************2");
		String userid = request.getParameter("uid");
		con = ConnectionUtil.getConnection();
		try {
			out = response.getWriter();
			dataArray = new JSONArray();
			System.out.println(URLDecoder.decode(contacts));
			org.json.JSONArray javaArray = new org.json.JSONArray(URLDecoder
					.decode(contacts));
			System.out.println("check********************************3");
			String query = "SELECT * FROM USER_DETAILS WHERE MOBILE_NUMBER=?";
			System.out.println(query + "jjjjjjjjjjjjjjjjjjj");
			int k = 0;
			PreparedStatement st = con.prepareStatement(query);
			for (int i = 0; i < javaArray.length(); i++) {
				String number = javaArray.getJSONObject(i).getString("number")
						.replace("-", "");

				st.setString(1, number);
				rs = st.executeQuery();
				while (rs.next()) {
					JSONObject object = new JSONObject();
					object.put("number", rs.getString("MOBILE_NUMBER"));
					object.put("name", rs.getString("name"));
					dataArray.add(object);
					// object.put("status", "status");
				}// while

			}// for
			out.print(dataArray);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st, rs);
			outClose(out);

		}

	}

	private void setstatus(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String status = request.getParameter("status");
		String number = request.getParameter("number");
		String fnumber = request.getParameter("fnumber");
		con = ConnectionUtil.getConnection();
		try {
			st = con.createStatement();
			out = response.getWriter();
			String query = "UPDATE FRIEND_LIST SET STATUS='" + status
					+ "' WHERE MOBILE_NUMBER=" + number
					+ " AND FMOBILE_NUMBER=" + fnumber;
			System.out.println(query);
			int i = st.executeUpdate(query);
			if (i > 0) {

				out.print("success");
			} else {
				out.print("fail");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st);
			outClose(out);

		}
	}

	private void getQuestion(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		con = ConnectionUtil.getConnection();
		dataArray = new JSONArray();
		try {
			st = con.createStatement();
			out = response.getWriter();
			String query = "select * from security_ques";
			System.out.println(query);
			rs = st.executeQuery(query);

			while (rs.next()) {
				System.out.println(rs.getString(1));
				JSONObject object = new JSONObject();
				object.put("qid", rs.getString(1));
				object.put("ques", rs.getString(2));

				dataArray.add(object);
			}
			out.print(dataArray);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st, rs);
			outClose(out);

		}

	}

	private void userLogin(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");

		con = ConnectionUtil.getConnection();
		try {
			st = con.createStatement();
			out = response.getWriter();
			String query = "select * from user_details where mobile_number='"
					+ userid + "' and password='" + password + "'";
			System.out.println(query);
			rs = st.executeQuery(query);
			if (rs.next()) {
				out.print("success");
			} else {
				out.print("fail");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st, rs);
			outClose(out);

		}
	}

	private void userRegistration(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String username = request.getParameter("usern").trim();
		String userid = request.getParameter("uid").trim();
		String pwd = request.getParameter("pwd").trim();
		String dob = request.getParameter("dob").trim();
		String mno = request.getParameter("mno").trim();
		String ans = request.getParameter("ans").trim();
		String radius = request.getParameter("radius").trim();

		String lat = request.getParameter("latitude").trim();
		String lon = request.getParameter("longitude").trim();
		String qid = request.getParameter("qid").trim();

		con = ConnectionUtil.getConnection();
		try {
			out = response.getWriter();
			st = con.createStatement();

			String query = "insert into user_details values('" + username
					+ "','" + userid + "','" + pwd + "','" + dob + "'," + mno
					+ ",'" + lat + "','" + lon + "','" + ans + "','" + qid
					+ "'," + radius + ")";
			System.out.println(query);
			int count = st.executeUpdate(query);
			if (count > 0) {
				out.print("success");
			} else {
				out.print("fail");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st, rs);
			outClose(out);

		}

	}

	private void forgotpasssword(HttpServletRequest request,
			HttpServletResponse response) {

		con = ConnectionUtil.getConnection();
		try {
			st = con.createStatement();
			out = response.getWriter();
			String userid = request.getParameter("userid").trim();
			String query = "select q.QUESTION,u.password,u.security_ans from user_details u,security_ques q where u.mobile_number='"
					+ userid + "' and u.QUES_ID=q.QUES_ID";
			System.out.println(query);
			rs = st.executeQuery(query);
			JSONObject object = new JSONObject();
			if (rs.next()) {

				object.put("qname", rs.getString(1));
				object.put("password", rs.getString(2));
				object.put("answer", rs.getString(3));
			} else {
				object.put("qname", "invalid");
				object.put("password", "invalid");
				object.put("answer", "invalid");
			}
			out.print(object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.connectionClose(con, st, rs);
			outClose(out);

		}

	}

	void outClose(PrintWriter out) {
		if (out != null) {
			out.close();
		}
	}
}
