import java.util.*;

class Car {
    private String carId;

    private String brand ;

    private String model;

    private double basepriceperday;

    private boolean isAvailable ;

    public Car (String carId ,String brand , String model ,double basepriceperday){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basepriceperday = basepriceperday;
        this.isAvailable = true ;
    }

    public String getcarId(){
        return carId;
    }

    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public double calculateprice (int rentaldays){
        return basepriceperday*rentaldays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent (){
        isAvailable = false;
    }
    public void returncar(){
        isAvailable = true;
    }
}   // class Car ends


class Customer{
    private String customerId;
    private String name ;

    public Customer(String customeId , String name ){
        this.customerId = customeId;
        this.name = name ;
    }

    public String getCustomerId(){
        return customerId;
    }

    public String getName(){
        return name ;
    }
} //customer ends


class Rental {
     private Car car ;   //making object car of car type 

     private Customer customer ;   //customer variable of customer type

     private int days;

     public Rental (Car car , Customer customer, int days){
        this.car=car;
        this.customer = customer ;
        this.days=days;
     }
     public Car getCar(){
        return car ;
     }

     public Customer getCustomer (){
        return customer ;
     }

     public int getDays(){
        return days;
     }
}//Rental ends



class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar (Car car ){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    } 

    public void rentCar(Car car , Customer customer , int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent ");
        }
    }

    public void returnCar (Car car ){
        car.returncar();
        Rental rentalToRemove = null;   // rentatoremove a variable of rental type 
        for (Rental rental : rentals ){   //for each loop
            if (rental.getCar() == car){   // if rental contains the car 
                rentalToRemove = rental ;
                break ;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
            System.out.println("Car returnedd succesfully");

        }else{
            System.out.println("Car was not rented");
        }
    }

    public void menu (){
        Scanner scanner = new Scanner (System.in);
        while (true){
            System.out.println("________Car Rental System_________");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice :");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (choice == 1){
                System.out.println("\n ___Rent a Car__\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for (Car car : cars){  // for each loop or the enhanced for loop is used to iterate over elements in an arry or a coolection . It traverses wihtout needing explicit indexing 
                    if(car.isAvailable()){
                        System.out.println(car.getcarId() +  "_" + car.getBrand() + "_" + car.getModel());
                    }
                }
                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentaldays = scanner.nextInt();
                scanner.nextLine() ;

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName );
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars){
                    if (car.getcarId().equals(carId) && car.isAvailable()){
                        selectedCar = car ;
                        break ;
                    }
                }
                if (selectedCar != null){
                    double totalPrice = selectedCar.calculateprice(rentaldays);
                    System.out.println("\n__Rental Information__\n");
                    System.out.println("Customer ID:" + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " +selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentaldays);
                    System.out.printf("Total Price: $%.2f%n" , totalPrice );  // this statement is used to formate the output on the terminal (%.2f specifies floating point number with two decimal places )

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, newCustomer, rentaldays);
                        System.out.println("\nCar rented successfully");
                    }else{
                        System.out.println("\n Invalid car seleciton or car not available for rent .");

                    }

                }
            }else if (choice == 2){
                System.out.println("\n__Return a Car__\n");
                System.out.println("Enter the car ID you want to return :");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars){
                    if (car.getcarId().equals(carId) && !car.isAvailable()){
                        carToReturn = car; 
                        break;
                    }
                }

                if (carToReturn != null){
                    Customer customer = null;
                    for (Rental rental : rentals ){
                        if(rental.getCar() == carToReturn){
                            customer = rental.getCustomer() ;
                            break;
                        }
                    }
                if (customer != null){
                    returnCar(carToReturn);
                    System.out.println("Car returned successfully by " + customer.getName());

                }else{
                    System.out.println("Car was not rented or rental information is missing.");
                }
                }else{
                    System.out.println("Invalid car ID or car is not rented.");
                }
                
            } else if (choice == 3){
                break;
            }else{
                System.out.println("\nThank you for using the Car Rental System!");
            }
        } //while (true )   ends
    }
}//CarRentaSystem ends


public class main{

    public static void main (String args[]){
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Supra", 6000.0); // Different base price per day for each car
        Car car2 = new Car("C002", "Nissan", "Skyline GTR", 5000.0);
        Car car3 = new Car("C003", "Ford", "Mustang", 8000.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}