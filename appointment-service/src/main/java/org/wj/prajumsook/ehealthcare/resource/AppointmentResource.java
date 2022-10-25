package org.wj.prajumsook.ehealthcare.resource;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.ehealthcare.model.Appointment;
import org.wj.prajumsook.ehealthcare.model.AppointmentResponse;
import org.wj.prajumsook.ehealthcare.service.AppointmentService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/appointments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    @Inject
    AppointmentService appointmentService;

    @GET
    public AppointmentResponse findAll() {
        List<Appointment> appointments = appointmentService.findAll();
        return new AppointmentResponse()
            .setResult(appointments)
            .setCount(appointments.size());
    }

    @GET
    @Path("/{id}")
    public Appointment findById(@RestPath Long id) {
        return appointmentService.findById(id);
    }

    @GET
    @Path("/doctor/{id}")
    public List<Appointment> findByDoctorId(@RestPath Long id) {
        return appointmentService.findByDoctorId(id);
    }

    @GET
    @Path("/patient/{id}")
    public List<Appointment> findByPatientId(@RestPath Long id) {
        return appointmentService.findByPatientId(id);
    }

    @POST
    public Appointment create(Appointment appointment) {
        return appointmentService.create(appointment);
    }

    @PUT
    public Appointment update(Appointment appointment) {
        return appointmentService.update(appointment);
    }

    @DELETE
    @Path("/{id}")
    public Appointment delete(@RestPath Long id) {
        return appointmentService.delete(id);
    }

}