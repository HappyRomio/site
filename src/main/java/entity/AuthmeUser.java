package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "authme",
uniqueConstraints = { //
        @UniqueConstraint(name = "AUTH_USER_UK", columnNames = "userName") })
public class AuthmeUser {
	@Id
	private Long id;

	private String username;
	
	private String realname;
	
	private String password;
	
	private String ip;
	
	private Long lastlogin;
	
	private double x;
	
	private double y;
	
	private double z;
	
	private String world;
	
	private int regdate;
	
	private String regip;
	
	private float yaw;
	
	private float pitch;
	
	private String email;
	
	private short isLoged;
	
	private short hasSession;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Long lastlogin) {
		this.lastlogin = lastlogin;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public int getRegdate() {
		return regdate;
	}

	public void setRegdate(int regdate) {
		this.regdate = regdate;
	}

	public String getRegip() {
		return regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public short getIsLoged() {
		return isLoged;
	}

	public void setIsLoged(short isLoged) {
		this.isLoged = isLoged;
	}

	public short getHasSession() {
		return hasSession;
	}

	public void setHasSession(short hasSession) {
		this.hasSession = hasSession;
	}

	public AuthmeUser() {
		super();
	}
}
