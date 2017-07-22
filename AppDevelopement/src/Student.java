 
import talkmongo.representation.annotations.Table;
import talkmongo.representation.annotations.Column;
import talkmongo.representation.Entity;

@Table (tableName = "student")
public class Student implements Entity{
	//@Column (columnName = "id", isPrimaryKey = true)
	//public int id;
	
	@Column (columnName = "firstName", isPrimaryKey = true)
	public String firstName;
	
	@Column (columnName = "lastName", isPrimaryKey = true)
	public String lastName;
	
	@Column (columnName = "age", isPrimaryKey = true)
	public int age;
	
	@Column (columnName = "gender", isPrimaryKey = true)
	public String gender;  // M, F      

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	/*public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}*/

	@Override
	public String toString(){
		return firstName+" : " +lastName+" : " +age+" : " +gender;
	}
}