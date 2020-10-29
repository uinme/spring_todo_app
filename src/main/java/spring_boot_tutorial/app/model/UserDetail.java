package spring_boot_tutorial.app.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetail implements UserDetails {

  private static final long serialVersionUID = 1L;
  private final UserModel userModel;
  private final Collection<? extends GrantedAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public int getUserId() {
    return userModel.getId();
  }

  @Override
  public String getPassword() {
    return userModel.getPassword();
  }

  @Override
  public String getUsername() {
    return userModel.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return userModel.isEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return userModel.isEnabled();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return userModel.isEnabled();
  }

  @Override
  public boolean isEnabled() {
    return userModel.isEnabled();
  }
  
}
