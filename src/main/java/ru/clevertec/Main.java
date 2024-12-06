package ru.clevertec;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.entity.Category;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.service.CarService;
import ru.clevertec.service.CarShowroomService;
import ru.clevertec.service.CategoryService;
import ru.clevertec.service.ClientService;
import ru.clevertec.service.ReviewService;
import ru.clevertec.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CarService carService = new CarService();
        ClientService clientService = new ClientService();
        CarShowroomService showroomService = new CarShowroomService();
        CategoryService categoryService = new CategoryService();
        ReviewService reviewService = new ReviewService();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // === CRUD для всех сущностей ===
            // Создание автосалонов
            CarShowroom showroom1 = new CarShowroom();
            showroom1.setName("Elite Motors");
            showroom1.setAddress("123 Main Street");
            showroomService.addShowroom(showroom1);

            CarShowroom showroom2 = new CarShowroom();
            showroom2.setName("Luxury Cars");
            showroom2.setAddress("456 Elm Street");
            showroomService.addShowroom(showroom2);

            // Создание категорий
            Category categorySedan = new Category();
            categorySedan.setName("Sedan");
            categoryService.addCategory(categorySedan);

            Category categorySUV = new Category();
            categorySUV.setName("SUV");
            categoryService.addCategory(categorySUV);

            // Добавление автомобилей
            Car car1 = new Car("Camry", "Toyota", 2022, 30000.0, categorySedan, showroom1);
            Car car2 = new Car("Accord", "Honda", 2021, 28000.0, categorySedan, showroom1);
            Car car3 = new Car("RAV4", "Toyota", 2023, 35000.0, categorySUV, showroom2);
            Car car4 = new Car("CR-V", "Honda", 2022, 34000.0, categorySUV, showroom2);

            carService.addCar(car1);
            carService.addCar(car2);
            carService.addCar(car3);
            carService.addCar(car4);

            // Обновление автомобиля
            car1.setPrice(31000.0);
            carService.updateCar(car1);

            // Удаление автомобиля
            carService.deleteCar(car2);

            // === Привязка автомобиля к автосалону ===
            Car car5 = new Car("Civic", "Honda", 2024, 29000.0, categorySedan, null);
            carService.addCar(car5);
            carService.assignCarToShowroom(car5, showroom2);

            // === Регистрация клиентов ===
            Client client1 = new Client();
            client1.setName("Alice Johnson");
            client1.setRegistrationDate(LocalDate.now());
            clientService.addClient(client1);

            Client client2 = new Client();
            client2.setName("Bob Smith");
            client2.setRegistrationDate(LocalDate.now());
            clientService.addClient(client2);

            // === Привязка автомобиля к клиенту ===
            clientService.buyCar(client1, car1);
            clientService.buyCar(client2, car3);

            // === Добавление отзывов ===
            reviewService.addReview(client1, car1, "Great performance and comfort!", 5);
            reviewService.addReview(client2, car3, "Excellent SUV with plenty of space.", 4);

            // === Поиск автомобилей ===
            // Поиск по параметрам
            System.out.println("\n=== Автомобили Toyota ===");
            List<Car> toyotaCars = carService.findCarsByFilters("Toyota", null, null, 0.0, 0.0);
            toyotaCars.forEach(System.out::println);

            // Сортировка по цене
            System.out.println("\n=== Автомобили, отсортированные по цене (убывание) ===");
            List<Car> sortedCars = carService.findCarsSortedByPrice(false);
            sortedCars.forEach(System.out::println);

            // Пагинация
            System.out.println("\n=== Пагинация автомобилей (страница 1, размер 2) ===");
            List<Car> paginatedCars = carService.findCarsWithPagination(1, 2);
            paginatedCars.forEach(System.out::println);

            // === Полнотекстовый поиск отзывов ===
            // По ключевым словам
            System.out.println("\n=== Поиск отзывов с ключевым словом 'performance' ===");
            List<Review> reviewsByKeyword = reviewService.searchReviews("performance");
            reviewsByKeyword.forEach(System.out::println);

            // Поиск отзывов по рейтингу
            System.out.println("\n=== Поиск отзывов с рейтингом 5 ===");
            List<Review> reviewsByRating = reviewService.findReviewsByRating(5);
            reviewsByRating.forEach(System.out::println);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
