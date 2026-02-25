package com.boot.mvc.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity   // Marks this class as JPA Entity (mapped to DB table)
@Table(name="DOCTORS_INFO")   // Specifies table name in database
@AllArgsConstructor  // Lombok: Generates all-args constructor
@NoArgsConstructor   // Lombok: Generates no-args constructor (required by JPA)
@Data   // Lombok: Generates getters, setters, toString(), equals(), hashCode()
@SQLDelete(sql="UPDATE DOCTORS_INFO SET STATUS='INACTIVE' WHERE ID=? AND UPDATE_COUNT=?") //FOR SOFT DELETION
@SQLRestriction("STATUS <> 'INACTIVE'") //FOR MAKING INACTIVE RECORDS NOT PARTICIPATING IN PERSISTANCE OPERATIONS
public class DoctorEntity {
	
	// ========================= ENTITY DATA (Business Fields) =========================
	
	@Id   // Marks this field as Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// IDENTITY → Auto-increment column in database
	private Integer id;
	
	@NotBlank  // Validation: String must not be null or empty
	@Column(length = 30)  // Column length in DB
	private String name;
	
	@NotBlank
	@Column(length = 30)
	private String specialization;
	
	@NotNull  // Validation: Cannot be null
	private Double fee;
	
	@NotNull
	@Column(length = 10)
	private Long phone;
	
	
	// ========================= META DATA (Audit Fields) =========================
	
	@CreationTimestamp  
	// Automatically sets current timestamp when record is inserted
	@Column(updatable = false, nullable = false)
	// updatable=false → value cannot be modified after insert
	private LocalDateTime createdOn;
	
	@UpdateTimestamp  
	// Automatically updates timestamp whenever record is updated
	@Column(nullable = false)
	private LocalDateTime updatedOn;
	
	
	@Column(nullable = false, updatable = false)
	// Set only once when record is created
	private String createdBy;
	
	
	@Column(nullable = false)
	// Updated every time record is modified
	private String updatedBy;
	
	@Column(nullable = false)
	// Updated user status will come
	private String status ="active";
	
	
	// ========================= JPA CALLBACK METHODS =========================
	
	@PrePersist
	// This method runs automatically BEFORE insert operation
	public void onCreate() {
	    this.createdBy = System.getProperty("user.name");
	    this.updatedBy = System.getProperty("user.name");
	}

	@PreUpdate
	// This method runs automatically BEFORE update operation
	public void onUpdate() {
	    this.updatedBy = System.getProperty("user.name");
	}

	
	// ========================= OPTIMISTIC LOCKING =========================
	
	@Version
	// Used for Optimistic Locking
	// Prevents Lost Update problem (Concurrency control)
	private Integer updateCount;
}
