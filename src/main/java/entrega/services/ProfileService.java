package entrega.services;

import entrega.models.Profile;
import entrega.repositories.ProfileRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProfileService {

    @Inject
    ProfileRepository profileRepository;

    public List<Profile> listAll() {
        return profileRepository.listAll();
    }

    public Optional<Profile> findById(Long id) {
        return Optional.ofNullable(profileRepository.findById(id));
    }

    @Transactional
    public Profile create(@Valid Profile profile) {
        profileRepository.persist(profile);
        return profile;
    }

    @Transactional
    public Optional<Profile> update(Long id, @Valid Profile profileData) {
        Profile profile = profileRepository.findById(id);
        if (profile != null) {
            profile.address = profileData.address;
            profile.phone = profileData.phone;
            profile.city = profileData.city;
            profile.state = profileData.state;
            profile.zipCode = profileData.zipCode;
            profileRepository.persist(profile);
            return Optional.of(profile);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean delete(Long id) {
        Profile profile = profileRepository.findById(id);
        if (profile != null) {
            profileRepository.delete(profile);
            return true;
        }
        return false;
    }

    public Optional<Profile> findByCustomerId(Long customerId) {
        Profile profile = profileRepository.findByCustomerId(customerId);
        return Optional.ofNullable(profile);
    }

    public List<Profile> findByCity(String city) {
        return profileRepository.findByCity(city);
    }

    public List<Profile> findByState(String state) {
        return profileRepository.findByState(state);
    }
}
