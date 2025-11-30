package com.devLucas.ControleGastos.entity;

import com.devLucas.ControleGastos.entity.enums.currentSituation;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_accounts")
public class AccountsPayable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private Double amount;
	private LocalDate dueDate;
	private String store;

	private currentSituation situation;

	public AccountsPayable() {
	}

	public AccountsPayable(Long id, String title, Double amount, LocalDate dueDate, String store,
			currentSituation situation) {
		super();
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.dueDate = dueDate;
		this.store = store;
		this.situation = situation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public currentSituation getSituation() {
		return situation;
	}

	public void setSituation(currentSituation situation) {
		this.situation = situation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountsPayable other = (AccountsPayable) obj;
		return Objects.equals(id, other.id);
	}

    @Override
    public String toString() {
        return "AccountsPayable{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", dueDate=" + dueDate +
                ", store='" + store + '\'' +
                ", situation=" + situation +
                '}';
    }
}
