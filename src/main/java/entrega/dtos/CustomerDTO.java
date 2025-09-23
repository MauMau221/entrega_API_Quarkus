package entrega.dtos;
//DTO - Controla o que vai e o que volta da minha API
public class CustomerDTO {

    public Long id;
    public String name;
    public String email;

    public CustomerDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
