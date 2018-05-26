package ecjtu.husen.pojo.DTO;

import java.io.Serializable;

/**
 * @author 11785
 */
public class UrlVO implements Serializable {
    private Integer urlId;
    private String url;
    private String description;

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UrlVO{" +
                "urlId=" + urlId +
                ", url=" + url +
                ", description='" + description + '\'' +
                '}';
    }
}
