package dto;

public class CarsDto {
    {
//        serialNumber*	string
//        manufacture*	string
//        model*	string
//        year*	string
//        fuel*	string
//        seats*	integer($int32)
//        carClass*	string
//        pricePerDay*	number($double)
//        about	string
//        city*	string
//        lat	number($double)
//            lng	number($double)
//        image	string
//        owner	string
//        bookedPeriods	[BookedDto{
//        email	string
//        startDate	string($date)
//                endDate	string($date)
    //}]
        private String serialNumber;
        private String manufacture;
        private String model;           // обязательное
        private String year;            // обязательное
        private String fuel;            // обязательное
        private int seats;              // обязательное
        private String carClass;        // обязательное
        private double pricePerDay;     // обязательное
        private String about;
        private String city;            // обязательное
        private Double lat;
        private Double lng;
        private String image;
        private String owner;
        private List<BookedDto> bookedPeriods;

    }
}
