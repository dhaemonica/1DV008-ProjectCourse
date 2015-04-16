package se.lnu.c1dv008.timeline.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "strings")
public class TestString {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column()
	public String id;
	public String name;

	public TestString() {
	}

	public TestString(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public TestString(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		return String.format("Name: %s, id: %s", name, id);
	}
}


