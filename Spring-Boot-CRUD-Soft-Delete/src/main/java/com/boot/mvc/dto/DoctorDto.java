package com.boot.mvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data   // Generates getters, setters, toString(), equals(), hashCode()
public class DoctorDto {

	private Integer id;   // Doctor ID

	@NotBlank   // Cannot be null or empty
	private String name;

	@NotBlank
	private String specialization;

	@NotNull    // Must not be null
	private Double fee;

	@NotNull    // Calculated in service (fee + GST)
	private Double netfee;

	@NotNull
	private Long phone;   // Contact number
}
