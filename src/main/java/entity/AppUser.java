package entity;


import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "App_User",
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_USER_UK", columnNames = "userName")})
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "User_Id", nullable = false)
    private Long userId;

    // @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    //@Column(name = "Ecrypted_Password", length = 128, nullable = false)
    private String encrytedPassword;

    //@Column(name = "EMAIL", length = 50, nullable = false)
    private String email;

    @Column(name = "CREDIT", nullable = false, columnDefinition = "int default 100")
    private Long credits;

    // @Column(name = "Enabled", length = 1, nullable = true)
    private Boolean enabled;

    private String activationCode;

    private Boolean subscription;

    private String subscriptionType;

    @Temporal(TemporalType.TIMESTAMP)
    //private java.util.Date dateSubscriptionStart;
    private Calendar dateSubscriptionStart;

    @Temporal(TemporalType.TIMESTAMP)
    //private java.util.Date dateSubscriptionEnd;
    private Calendar dateSubscriptionEnd;

    public Calendar getDateSubscriptionStart() {
        return dateSubscriptionStart;
    }

    public void setDateSubscriptionStart(Calendar dateSubscriptionStart) {
        this.dateSubscriptionStart = dateSubscriptionStart;
    }

    public Calendar getDateSubscriptionEnd() {
        return dateSubscriptionEnd;
    }

    public void setDateSubscriptionEnd(Calendar dateSubscriptionEnd) {
        this.dateSubscriptionEnd = dateSubscriptionEnd;
    }

    public Boolean getSubscription() {
        return subscription;
    }

    public void setSubscription(Boolean subscription) {
        this.subscription = subscription;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public AppUser() {

    }

    public AppUser(String login, String pass, boolean flag) {
        this.userName = login;
        this.encrytedPassword = pass;
        this.enabled = flag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}