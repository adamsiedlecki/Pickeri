package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private LocalDateTime time;

    public Note(Long id, String content, LocalDateTime time) {
        this.id = id;
        this.content = content;
        this.time = time;
    }

    public Note() {
    }

    public LocalDateTime getTime() {
        if (time == null) {
            return LocalDateTime.MIN;
        }
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
