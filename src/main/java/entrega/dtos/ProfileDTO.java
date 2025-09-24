package entrega.dtos;

public class ProfileDTO {
    public Long id;
    public String address;
    public String phone;
    public String city;
    public String state;
    public String zipCode;
    public Long customerId;

    public ProfileDTO() {}

    public ProfileDTO(Long id, String address, String phone, String city, 
                     String state, String zipCode, Long customerId) {
        this.id = id;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.customerId = customerId;
    }
}
