package spring_boot_tutorial.app.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignupFormModel {
  
  @NotBlank(message = "{signupFrom.email.blank}")
  @Email(message = "{signup_form_email_is_invalid_format}")
  private String email;

  @NotBlank(message = "{signup_form_username_is_blank}")
  @Length(max = 50, message = "{signup_form_username_length_greater_than_max_length}")
  private String username;

  @NotBlank(message = "{signup_form_password_is_blank}")
  @Length(min = 6, max = 20, message = "{signup_form_password_length_is_out_of_range}")
  private String password;

  @NotBlank(message = "{signup_form_confirm_password_is_blank}")
  private String confirmPassword;

  @AssertTrue(message = "{signup_form_confirm_password_is_not_match_password}")
  public boolean isMatchConfirmPassword() {

    if (password.equals(confirmPassword)) {

      return true;

    }

    return false;

  }

}
