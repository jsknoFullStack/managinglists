package com.jskno.managinglistsbe.security.persistence;

import javax.persistence.*;

@Entity
@Table(name = "accounts_users_roles")
public class AccountUserRole {

    @EmbeddedId
    private AccountUserRoleId id;

    @MapsId("account_id")
    @ManyToOne
    private Account account;

    @MapsId("user_id")
    @ManyToOne
    private User user;

    @MapsId("role_id")
    @ManyToOne
    private Role role;

    public AccountUserRole() {
    }

    public AccountUserRole(Account account, User user, Role role) {
        this.account = account;
        this.id.setAcoountId(account.getId());

        this.user = user;
        this.id.setUserId(user.getId());

        this.role = role;
        this.id.setRoleId(role.getId());
    }

    public AccountUserRoleId getId() {
        return id;
    }

    public void setId(AccountUserRoleId id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
