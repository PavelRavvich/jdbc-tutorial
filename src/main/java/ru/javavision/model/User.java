package ru.javavision.model;

import lombok.*;

/**
 * Author : Pavel Ravvich.
 * Created : 06/11/2017.
 * <p>
 * User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;

    private String login;

    private String password;

    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return login.equals(user.login) && password.equals(user.password) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Data
    @AllArgsConstructor
    public static class Role {

        private int id;

        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Role)) return false;
            Role role = (Role) o;
            return name.equals(role.name);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + name.hashCode();
            return result;
        }
    }
}
