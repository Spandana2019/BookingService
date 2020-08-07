package com.example.bookingapplication;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.example.bookingapplication.dto.AddressDto;
import com.example.bookingapplication.dto.BookingDto;
import com.example.bookingapplication.dto.BookingResponse;
import com.example.bookingapplication.dto.TimeSlotDto;
import com.example.bookingapplication.model.Address;
import com.example.bookingapplication.model.Booking;
import com.example.bookingapplication.model.Contact;
import com.example.bookingapplication.model.TimeSlot;
import com.example.bookingapplication.repository.BookingRepository;
import com.example.bookingapplication.repository.ContactRepository;
import com.example.bookingapplication.repository.TimeSlotRepository;
import com.example.bookingapplication.service.BookingService;
import com.example.bookingapplication.utility.Mapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ControllerTestCase {

	@LocalServerPort
	private int PORT;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private TimeSlotRepository timeSlotRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private Mapper mapper;
	
	private final String TIME_PATTERN = "hh:mm a";
	private final String DATE_PATTERN = "MM-dd-yyyy";

	// private TimeSlot timeSlot;
	// private static TimeSlotDto timeSlotDto;
	// private static Booking booking;
	// private BookingDto bookingDto;
	// private static Contact contact;
	// private static Address address;
	// private AddressDto addressDto;

//	@BeforeEach 
//	void setup() {
//		timeSlot = new TimeSlot();
//		timeSlot.setAvailable(true);
//		timeSlot.setStartTime(LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse("15:00")));
//		timeSlot.setEndTime(LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse("16:00")));
//		timeSlot = timeSlotRepository.save(timeSlot);
//
//		bookingDto = new BookingDto();
//		bookingDto.setBookingDate("07-30-2020");
//		bookingDto.setName("testname");
//		bookingDto.setEmail("test@gmail.com");
//		bookingDto.setPhone("1234567890");
//		bookingDto.setSlotId(timeSlot.getSlotId());
//		addressDto = new AddressDto();
//		addressDto.setStreet("Street 123");
//		addressDto.setCity("City");
//		addressDto.setState("IL");
//		addressDto.setZip("12345");
//		bookingDto.setAddress(addressDto);
//	}

	@Test
	void saveABooking() {

		// Arrange
		TimeSlot slot = new TimeSlot();
		slot.setAvailable(true);
		
		LocalTime time = LocalTime.now();
		slot.setDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse("07-30-2020")));
		slot.setStartTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("07:00 AM")));
		slot.setEndTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("08:00 AM")));
		slot = timeSlotRepository.save(slot);

		System.out.println("Slot id : " + slot.getSlotId());

		TimeSlotDto timeSlotDto = new TimeSlotDto();
		timeSlotDto.setStartTime(slot.getStartTime());
		timeSlotDto.setEndTime(slot.getEndTime());
		timeSlotDto.setAvailable(true);
		timeSlotDto.setSlotId(slot.getSlotId());

		System.out.println("Slot dto id : " + timeSlotDto.getSlotId());

		BookingDto bookingDto = new BookingDto();
		bookingDto.setBookingDate(LocalDate.parse("07-30-2020", DateTimeFormatter.ofPattern("MM-dd-yyyy")));
		bookingDto.setName("testname");
		bookingDto.setEmail("test@gmail.com");
		bookingDto.setPhone("1234567890");
		//bookingDto.setDateOfBirth("06-12-1999");
		bookingDto.setSlot(timeSlotDto);
		AddressDto addressDto = new AddressDto();
		addressDto.setStreet("Street 123");
		addressDto.setCity("City");
		addressDto.setState("IL");
		addressDto.setZip("12345");
		bookingDto.setAddress(addressDto);

		// Act
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<BookingDto> httpEntity = new HttpEntity<>(bookingDto, header);
		ResponseEntity<BookingDto> response = testRestTemplate
				.postForEntity("http://localhost:" + PORT + "/api/bookings", httpEntity, BookingDto.class);

		// Assert
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody().getBookingId()).isGreaterThan(0);
		bookingDto.setBookingId(response.getBody().getBookingId());
		assertThat(response.getBody().getName()).isEqualTo("testname");
		assertThat(timeSlotRepository.findById(slot.getSlotId()).get().isAvailable()).isEqualTo(false);
	}

	@Test
	void getAllBookings() {

		// Arrange
		TimeSlot slot = new TimeSlot();
		slot.setAvailable(false);

		Booking booking = new Booking();
		booking.setBookingDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse("07-30-2020")));
		Contact contact = new Contact();
		contact.setName("testname");
		contact.setEmail("test@gmail.com");
		contact.setPhone("1234567890");

		Address address = new Address();
		address.setStreet("Street 123");
		address.setCity("City");
		address.setState("IL");
		address.setZip("12345");
		contact.setAddress(address);
		booking.setContact(contact);

		slot.setBooking(booking);
		booking.setSlot(slot);
		contact.setBooking(booking);
		bookingRepository.save(booking);

		// Act
		// HttpHeaders headers = new HttpHeaders();
		// headers.add("Accept", "application/json");
		// HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<BookingResponse<BookingDto>> response = testRestTemplate.exchange(
				"http://localhost:" + PORT + "/api/bookings", HttpMethod.GET, null,
				new ParameterizedTypeReference<BookingResponse<BookingDto>>() {
				});

		System.out.println("response : " + response.getBody());

		// Assert
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody().getList().size()).isGreaterThan(0);
		assertThat(((BookingDto) response.getBody().getList().get(0)).getBookingId()).isGreaterThan(0);
		assertThat(((BookingDto) response.getBody().getList().get(0)).getSlot().getSlotId()).isGreaterThan(0);
	}

	@Test
	void updateABooking() {

		// Arrange
		TimeSlot slot = new TimeSlot();
		LocalTime time = LocalTime.now();
		slot.setDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse("07-30-2020")));
		slot.setStartTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("07:00 AM")));
		slot.setEndTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("08:00 AM")));
		slot.setAvailable(false);

		Booking booking = new Booking();
		booking.setBookingDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse("07-30-2020")));
		Contact contact = new Contact();
		contact.setName("testname");
		contact.setEmail("test@gmail.com");
		contact.setPhone("1234567890");

		Address address = new Address();
		address.setStreet("Street 123");
		address.setCity("City");
		address.setState("IL");
		address.setZip("12345");
		contact.setAddress(address);
		booking.setContact(contact);

		slot.setBooking(booking);
		booking.setSlot(slot);
		contact.setBooking(booking);
		booking = bookingRepository.save(booking);

		BookingDto bookingDto = mapper.mapModelToDto(booking);
		bookingDto.setName("changedName");
		// Act
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<BookingDto> entity = new HttpEntity<>(bookingDto, headers);
		ResponseEntity<BookingDto> response = testRestTemplate.exchange("http://localhost:" + PORT + "/api/bookings",
				HttpMethod.PUT, entity, BookingDto.class);

		// Assert
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody().getName()).isEqualTo("changedName");
		assertThat(bookingRepository.findById(response.getBody().getBookingId()).get().getContact().getName())
				.isEqualTo("changedName");
	}

	@Test
	void updateABookingSlot() {

		// Arrange
		TimeSlot slot = new TimeSlot();
		LocalTime time = LocalTime.now();
		slot.setDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse("07-30-2020")));
		slot.setStartTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("07:00 AM")));
		slot.setEndTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("08:00 AM")));
		slot.setAvailable(false);

		Booking booking = new Booking();
		booking.setBookingDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse("07-30-2020")));
		Contact contact = new Contact();
		contact.setName("testname");
		contact.setEmail("test@gmail.com");
		contact.setPhone("1234567890");

		Address address = new Address();
		address.setStreet("Street 123");
		address.setCity("City");
		address.setState("IL");
		address.setZip("12345");
		contact.setAddress(address);
		booking.setContact(contact);
		booking.setSlot(slot);
		slot.setBooking(booking);
		booking.setSlot(slot);
		contact.setBooking(booking);
		booking = bookingRepository.save(booking);

		BookingDto bookingDto = mapper.mapModelToDto(booking);

		TimeSlot slot2 = new TimeSlot();
		LocalTime time2 = LocalTime.now();
		slot2.setDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse("07-30-2020")));
		slot2.setStartTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("07:00 AM")));
		slot2.setEndTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("08:00 AM")));
		slot2 = timeSlotRepository.save(slot2);

		TimeSlotDto slotDto = new TimeSlotDto();
		slotDto.setStartTime(slot2.getStartTime());
		slotDto.setEndTime(slot2.getEndTime());
		slotDto.setAvailable(true);
		slotDto.setSlotId(slot2.getSlotId());
		bookingDto.setSlot(slotDto);

		// Act
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<BookingDto> entity = new HttpEntity<>(bookingDto, headers);
		ResponseEntity<BookingDto> response = testRestTemplate.exchange("http://localhost:" + PORT + "/api/bookings",
				HttpMethod.PUT, entity, BookingDto.class);

		// Assert
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(timeSlotRepository.findById(response.getBody().getSlot().getSlotId()).get().isAvailable())
				.isEqualTo(false);
		assertThat(timeSlotRepository.findById(booking.getSlot().getSlotId()).get().isAvailable()).isEqualTo(true);
	}

	@Test
	void deleteABooking() {
		// Arrange
		TimeSlot slot = new TimeSlot();
		LocalTime time = LocalTime.now();
		slot.setDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse("07-30-2020")));
		slot.setStartTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("07:00 AM")));
		slot.setEndTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("08:00 AM")));
		slot.setAvailable(false);

		Booking booking = new Booking();
		booking.setBookingDate(LocalDate.from(DateTimeFormatter.ofPattern("MM-dd-yyyy").parse("07-30-2020")));
		Contact contact = new Contact();
		contact.setName("testname");
		contact.setEmail("test@gmail.com");
		contact.setPhone("1234567890");

		Address address = new Address();
		address.setStreet("Street 123");
		address.setCity("City");
		address.setState("IL");
		address.setZip("12345");
		contact.setAddress(address);
		booking.setContact(contact);

		slot.setBooking(booking);
		booking.setSlot(slot);
		contact.setBooking(booking);
		booking = bookingRepository.save(booking);

		// Act
		ResponseEntity<BookingResponse> response = testRestTemplate.exchange(
				"http://localhost:" + PORT + "/api/bookings/" + booking.getBookingId(), HttpMethod.DELETE, null,
				BookingResponse.class);

		// Assert
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(bookingRepository.findById(booking.getBookingId()).isPresent()).isEqualTo(false);
		assertThat(contactRepository.findById(booking.getContact().getContactId()).isPresent()).isEqualTo(false);
		assertThat(timeSlotRepository.findById(booking.getSlot().getSlotId()).get().isAvailable()).isEqualTo(true);

	}
	
	@Test
	void getABooing() {
		// Arrange
		TimeSlot slot = new TimeSlot();
		LocalTime time = LocalTime.now();
		slot.setDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse("07-30-2020")));
		slot.setStartTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("07:00 AM")));
		slot.setEndTime(LocalTime.from(DateTimeFormatter.ofPattern(TIME_PATTERN).parse("08:00 AM")));
		slot.setAvailable(false);

		Booking booking = new Booking();
		booking.setBookingDate(LocalDate.from(DateTimeFormatter.ofPattern("MM-dd-yyyy").parse("07-30-2020")));
		Contact contact = new Contact();
		contact.setName("testname");
		contact.setEmail("test@gmail.com");
		contact.setPhone("1234567890");

		Address address = new Address();
		address.setStreet("Street 123");
		address.setCity("City");
		address.setState("IL");
		address.setZip("12345");
		contact.setAddress(address);
		booking.setContact(contact);

		slot.setBooking(booking);
		booking.setSlot(slot);
		contact.setBooking(booking);
		booking = bookingRepository.save(booking);
		
		//Act
		ResponseEntity<BookingDto> response = testRestTemplate.getForEntity("http://localhost:" + PORT + "/api/bookings/" + booking.getBookingId() , BookingDto.class);
	
	    //Assert
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody().getBookingId()).isEqualTo(booking.getBookingId());
		assertThat(response.getBody().getName()).isEqualTo(booking.getContact().getName());
	}

}
