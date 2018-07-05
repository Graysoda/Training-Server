package training.api.rest.jsonObjects;

public class ActorJson {
    private String firstName;
    private String lastName;

    public ActorJson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
