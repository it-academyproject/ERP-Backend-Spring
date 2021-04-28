package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Address;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IAddressRepository;

@Service
public class AddressServiceImpl implements IAddressService {
	
	@Autowired
	IAddressRepository iAddressRepository;
	
	@Override
	public Address createAddress(Address address) {
		
		return iAddressRepository.save(address);
	}

	@Override
	public Address findAddressById(UUID id) {
		
		return iAddressRepository.findById(id)
	            .orElseThrow(() -> new ArgumentNotFoundException("Address not found. The id " + id + " doesn't exist"));
	}

	@Override
	public List<Address> findAllAddresses() {
		if(iAddressRepository.findAll().isEmpty()){
		      throw new ArgumentNotFoundException("No addresses found");
		    }
		    return iAddressRepository.findAll();
	}

	@Override
	public Address updateAddress(Address address) {
		if(iAddressRepository.findById(address.getId()) == null){
		      throw new ArgumentNotFoundException("No address found");
		    }
		    return iAddressRepository.save(address);
	}

	@Override
	public void deleteAddress(UUID id) {
		iAddressRepository.deleteById(id);
		
	}
	
}
