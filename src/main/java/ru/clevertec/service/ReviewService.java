package ru.clevertec.service;

import org.hibernate.Session;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.repository.impl.CarRepository;
import ru.clevertec.repository.impl.ClientRepository;
import ru.clevertec.repository.impl.ReviewRepository;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class ReviewService {

    private final ReviewRepository reviewRepository = new ReviewRepository();
    private final ClientRepository clientRepository = new ClientRepository();
    private final CarRepository carRepository = new CarRepository();

    public void addReview(Long clientId, Long carId, String text, int rating) {
        Client client = clientRepository.findById(clientId);
        Car car = carRepository.findById(carId);

        if (client == null || car == null) {
            throw new IllegalArgumentException("Client or Car not found");
        }

        Review review = new Review();
        review.setClient(client);
        review.setCar(car);
        review.setText(text);
        review.setRating(rating);

        reviewRepository.save(review);
    }

    public void updateReview(Review review) {
        reviewRepository.update(review);
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

}
