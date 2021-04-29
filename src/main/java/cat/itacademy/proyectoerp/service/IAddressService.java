package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Address;

public interface IAddressService {

	 public Address createAddress(Address address);
	 
	 public Address findAddressById(UUID id);
	 
	 public List<Address> findAllAddresses();
	 
	 public Address updateAddress (Address address);

	 public void deleteAddress(UUID id);
}
