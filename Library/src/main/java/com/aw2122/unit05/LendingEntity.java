package com.aw2122.unit05;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "lending", schema = "public", catalog = "Library")
public class LendingEntity {
    private int id;
    private Date lendingdate;
    private Date returningdate;
    private BooksEntity book;
    private UsersEntity borrower;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lendingdate", nullable = false)
    public Date getLendingdate() {
        return lendingdate;
    }

    public void setLendingdate(Date lendingdate) {
        this.lendingdate = lendingdate;
    }

    @Basic
    @Column(name = "returningdate", nullable = true)
    public Date getReturningdate() {
        return returningdate;
    }

    public void setReturningdate(Date returningdate) {
        this.returningdate = returningdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LendingEntity that = (LendingEntity) o;
        return id == that.id && Objects.equals(lendingdate, that.lendingdate) && Objects.equals(returningdate, that.returningdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lendingdate, returningdate);
    }

    @ManyToOne
    @JoinColumn(name = "book", referencedColumnName = "isbn", nullable = false)
    public BooksEntity getBook() {
        return book;
    }

    public void setBook(BooksEntity book) {
        this.book = book;
    }

    @OneToOne
    @JoinColumn(name = "borrower", referencedColumnName = "code", nullable = false)
    public UsersEntity getBorrower() {
        return borrower;
    }

    public void setBorrower(UsersEntity borrower) {
        this.borrower = borrower;
    }
}
