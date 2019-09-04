package gamestore.domain.dtos;

import gamestore.domain.Messages;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GameAddDto {
    // |<title>|<price>|<size>|<trailer>|<thubnailURL>|<description>|<releaseDate>
    private String title;
    private BigDecimal price;
    private Double size;
    private String trailer;
    private String imageThumbnail;
    private String description;
    private LocalDate releaseDate;
    private String owner;

    public GameAddDto() {
    }

    public GameAddDto(String title, BigDecimal price, Double size, String trailer, String imageThumbnail, String description, LocalDate releaseDate, String owner) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.imageThumbnail = imageThumbnail;
        this.description = description;
        this.releaseDate = releaseDate;
        this.owner = owner;
    }

    @Pattern(regexp = "[A-Z]\\w{2,100}", message = Messages.TITLE_NOT_NULL)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //    @Pattern(regexp = "^([0-9]*?\\.)[0-9]{2}$", message = Messages.PRICE_CANNOT_BE_NULL)
    @NotNull(message = Messages.PRICE_CANNOT_BE_NULL)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //    @Pattern(regexp = "^([0-9]*?\\.)[0-9]{1}$", message = Messages.SIZE_CANNOT_BE_NULL)
    @NotNull(message = Messages.SIZE_CANNOT_BE_NULL)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Pattern(regexp = "^(.{11})$", message = Messages.TRAILER_CANNOT_BE_NULL)
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Pattern(regexp = "(http(s)?:\\/\\/)?(.+)", message = Messages.THUMBNAIL_ADD)
    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    @Length(min = 20, message = Messages.DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
