package org.example;

import javax.persistence.*;
import java.sql.Date;

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

        if (id != that.id) return false;
        if (lendingdate != null ? !lendingdate.equals(that.lendingdate) : that.lendingdate != null) return false;
        if (returningdate != null ? !returningdate.equals(that.returningdate) : that.returningdate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (lendingdate != null ? lendingdate.hashCode() : 0);
        result = 31 * result + (returningdate != null ? returningdate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "book", referencedColumnName = "isbn", nullable = false)
    public BooksEntity getBook() {
        return book;
    }

    public void setBook(BooksEntity book) {
        this.book = book;
    }

    @ManyToOne
    @JoinColumn(name = "borrower", referencedColumnName = "code", nullable = false)
    public UsersEntity getBorrower() {
        return borrower;
    }

    public void setBorrower(UsersEntity borrower) {
        this.borrower = borrower;
    }
}
