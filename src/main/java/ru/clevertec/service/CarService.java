package ru.clevertec.service;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.repository.impl.CarRepository;
import ru.clevertec.repository.impl.CarShowroomRepository;

import java.util.List;

public class CarService {

    private final CarRepository carRepository = new CarRepository();
    private final CarShowroomRepository carShowroomRepository = new CarShowroomRepository();

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public void updateCar(Car car) {
        carRepository.update(car);
    }

    public void deleteCar(Car car) {
        carRepository.delete(car);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void assignCarToShowroom(Long carId, Long showroomId) {
        Car car = carRepository.findById(carId);
        CarShowroom showroom = carShowroomRepository.findById(showroomId);

        if (car == null || showroom == null) {
            throw new IllegalArgumentException("Car or Showroom not found");
        }

        car.setShowroom(showroom);
        carRepository.update(car);
    }
}
