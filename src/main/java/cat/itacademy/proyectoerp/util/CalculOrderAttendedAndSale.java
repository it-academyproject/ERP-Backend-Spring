package cat.itacademy.proyectoerp.util;

import java.util.List;

import cat.itacademy.proyectoerp.domain.Order;

public class CalculOrderAttendedAndSale {

	public CalculOrderAttendedAndSale(){};
	public int getTotalOrdersAttendedbyEmployee(List<Order> listOrdersEmployee) {

		return listOrdersEmployee != null ? listOrdersEmployee.size() : 0;

	}

	public double getTotalSalesEmployee(List<Order> listOrdersEmployee) {
		double totalSalesEmpleyee = 0;
		if (listOrdersEmployee != null) {
			for (Order o : listOrdersEmployee) {
				totalSalesEmpleyee += o.getTotal();
			}
		}

		return totalSalesEmpleyee;
	}

}
