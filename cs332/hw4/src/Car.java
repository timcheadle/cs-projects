/**
 * Car is a container class to store attributes about a car.  It can store the following attributes:
 * 
 * <ul>
 * <li>Make
 * <li>Model
 * <li>Model Year
 * <li>Color
 * <li>Cylinders
 * <li>Horsepower
 * <li>Torque
 * </ul>
 * 
 * This class implements the {@link Comparable} interface so sorting a list of cars is possible.
 * 
 * @author Tim Cheadle
 */
public class Car implements Comparable {
	private String make;
	private String model;
	private int modelYear;
	private String color;
	private int horsepower;
	private int torque;
	private int cylinders;
	
	/**
	 * Constructs a <code>Car</code> object with default ("" or zeroed) values. 
	 */
	public Car() {
		this.make = new String();
		this.model        = new String();
		this.modelYear    = 0;
		this.color        = new String();
		this.horsepower   = 0;
		this.torque       = 0;
		this.cylinders    = 0;
	}
	
	/**
	 * Constructs a <code>Car</code> object with the given attributes.
	 * 
	 * @param make
	 * @param model
	 * @param modelYear
	 * @param color
	 * @param horsepower
	 * @param torque
	 * @param cylinders
	 */
	public Car (
		String make,
		String model,
		int modelYear,
		String color,
		int horsepower,
		int torque,
		int cylinders)
	{
		this.make         = make;
		this.model        = model;
		this.modelYear    = modelYear;
		this.color        = color;
		this.horsepower   = horsepower;
		this.torque       = torque;
		this.cylinders    = cylinders;
	}
	
	/**
	 * Compares the object to the given object.  Returns a negative integer if the
	 * object preceeds the given object, a positive integer if the given object
	 * preceeds the object, and a zero if they share the same order.
	 * 
	 * Car objects are compared with the following hierarchy:
	 * <ol>
	 * <li>make
	 * <li>model
	 * <li>model year
	 * <li>color
	 * <li>horsepower
	 * <li>torque
	 * <li>cylinders
	 * </ol>
	 * 
	 * @param o The object to compare to
	 * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 */
	public int compareTo(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		if (!(o instanceof Car)) {
			throw new ClassCastException();
		}
		
		Car c = (Car)o;
		
		if (this.make.compareTo(c.getMake()) != 0) {
			return this.make.compareTo(c.getMake());
		}
		if (this.model.compareTo(c.getModel()) != 0) {
			return this.model.compareTo(c.getModel());
		}
		if (this.modelYear != c.getModelYear()) {
			return this.modelYear - c.getModelYear();
		}
		if (this.color.compareTo(c.getColor()) != 0) {
			return this.color.compareTo(c.getColor());
		}
		if (this.horsepower != c.getHorsepower()) {
			return this.horsepower - c.getHorsepower();
		}
		if (this.torque != c.getTorque()) {
			return this.torque - c.getTorque();
		}
		if (this.cylinders != c.getCylinders()) {
			return this.cylinders - c.getCylinders();
		}
		
		return 0;
	}

	/**
	 * Returns a comma-delimited description of the car object as a <code>String</code>.
	 * 
	 * @return The car's description
	 */
	public String toString() {
		return make + "," +
			model + "," +
			modelYear + "," +
			color + "," +
			horsepower + "," +
			torque + "," +
			cylinders;
	}

	/**
	 * Returns the car's color as a <code>String</code>.
	 * 
	 * @return The car's color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Returns the car's number of cylinders as an integer.
	 * 
	 * @return The car's number of cylinders
	 */
	public int getCylinders() {
		return cylinders;
	}

	/**
	 * Returns the car's horsepower rating as an integer.
	 * 
	 * @return The car's horsepower
	 */
	public int getHorsepower() {
		return horsepower;
	}

	/**
	 * Returns the car's make (manufacturer) as a <code>String</code>.
	 * 
	 * @return The car's make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * Returns the car's model as a <code>String</code>.
	 * 
	 * @return The car's model.
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Returns the car's model year as an integer.
	 * 
	 * @return The car's model year
	 */
	public int getModelYear() {
		return modelYear;
	}

	/**
	 * Return's the car's torque rating as an integer.
	 * 
	 * @return The car's torque
	 */
	public int getTorque() {
		return torque;
	}

	/**
	 * Sets the car's color.
	 * 
	 * @param string A color
	 */
	public void setColor(String string) {
		color = string;
	}

	/**
	 * Sets the number of cylinders in the car's engine.
	 * 
	 * @param i Number of cylinders
	 */
	public void setCylinders(int i) {
		cylinders = i;
	}

	/**
	 * Sets the horsepower rating of the car's engine.
	 * 
	 * @param i Amount of horsepower
	 */
	public void setHorsepower(int i) {
		horsepower = i;
	}

	/**
	 * Sets the car's make (manufacturer).
	 * 
	 * @param string A make
	 */
	public void setMake(String string) {
		make = string;
	}

	/**
	 * Sets the car's model name.
	 * 
	 * @param string A model name
	 */
	public void setModel(String string) {
		model = string;
	}

	/**
	 * Sets the car's model year.
	 * 
	 * @param i A model year
	 */
	public void setModelYear(int i) {
		modelYear = i;
	}

	/**
	 * Sets the torque rating for the car's engine.
	 * 
	 * @param i Amount of torque
	 */
	public void setTorque(int i) {
		torque = i;
	}
}
