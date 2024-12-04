package ru.clevertec;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.entity.Category;
import ru.clevertec.entity.Client;
import ru.clevertec.service.CarService;
import ru.clevertec.service.CarShowroomService;
import ru.clevertec.service.CategoryService;
import ru.clevertec.service.ClientService;
import ru.clevertec.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {

        CarService carService = new CarService();
        ClientService clientService = new ClientService();
        CarShowroomService showroomService = new CarShowroomService();
        CategoryService categoryService = new CategoryService();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            CarShowroom showroom = new CarShowroom();
            showroom.setName("Elite Motors");
            showroom.setAddress("123 Main Street");
            showroomService.addShowroom(showroom);

            Category category = new Category();
            category.setName("Sedan");
            categoryService.addCategory(category);

            Car car = new Car();
            car.setModel("Camry");
            car.setBrand("Toyota");
            car.setYear(2022);
            car.setPrice(30000);
            car.setCategory(category);
            car.setShowroom(showroom);
            carService.addCar(car);

            Client client = new Client();
            client.setName("John Doe");
            clientService.addClient(client);

            clientService.buyCar(client.getId(), car.getId());

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
