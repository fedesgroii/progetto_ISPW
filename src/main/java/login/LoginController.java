package login;

// Control Class
class LoginController {
    private final LoginModel entity = new LoginModel();

    public void handleSpecialistLogin() {
        entity.setRole("Specialist");
 // to implement
    }

    public void handlePatientLogin() {
        entity.setRole("Patient");
    // to implement
    }

    public void handleAppointmentWithoutLogin() {
// to implement

    }
}
