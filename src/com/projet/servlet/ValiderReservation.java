package com.projet.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.projet.dao.DAOFactory;
import com.projet.dao.Reservationdao;
import com.projet.dao.VolDAO;
import com.projet.model.Reservation;
import com.projet.model.Utilisateur;
import com.projet.service.ReservationService;

/**
 * Servlet implementation class ValiderReservation
 */
@WebServlet("/ValiderReservations")
public class ValiderReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY= "daofactory";
	public static final String ATT_CLIENT = "reservation";
	public static final String ATT_FORM = "form";
	public static final String VUE = "/MesVoyage.jsp";
	private Reservationdao reservationdao;
	private VolDAO voldao;
	private  DAOFactory daofactory;
    public ValiderReservation() {
        super();
        // TODO Auto-generated constructor stub
    }

      public void init() throws ServletException {
    	
		this.daofactory= (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY );
		this.reservationdao=daofactory.getReservationDao();
		this.voldao=daofactory.getVolDAO();
		}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReservationService form = new ReservationService(reservationdao,voldao);
		/* Traitement de la requ�te et r�cup�ration du bean e
		r�sultant */
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");
		utilisateur = form.ValiderReservation( request,utilisateur);
		request.setAttribute(ATT_FORM , form );
		request.setAttribute("erreurs", form.getErreurs());
		session.setAttribute("sessionUtilisateur", utilisateur);
		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}

	

}
