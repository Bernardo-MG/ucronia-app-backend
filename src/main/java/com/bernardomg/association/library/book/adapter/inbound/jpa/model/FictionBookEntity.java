
package com.bernardomg.association.library.book.adapter.inbound.jpa.model;

import java.io.Serializable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity(name = "FictionBook")
@DiscriminatorValue("fiction")
public class FictionBookEntity extends RootBookEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = -6518446575924375762L;

    @Override
    public String toString() {
        return "FictionBookEntity [toString()=" + super.toString() + "]";
    }

}
