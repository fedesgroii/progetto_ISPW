package login;

// Control Class
class LoginController {
    private final LoginModel entity = new LoginModel();

    public void handleSpecialistLogin() {
        entity.setRole("Specialist");
      //  System.out.println("Login as Specialist");
        // Implement login logic here
    }

    public void handlePatientLogin() {
        entity.setRole("Patient");
        //System.out.println("Login as Patient");
        // Implement login logic here
    }

    public void handleAppointmentWithoutLogin() {
        //System.out.println("Booking without login");
        // Implement logic here
    }
}
