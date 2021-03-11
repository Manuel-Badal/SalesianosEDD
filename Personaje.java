package repaso;

public class Personaje {
	private String nombre;
	private int hp;
	
	public Personaje(String nombre) {
		this.nombre = nombre;
		hp = 100;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
}
