package com.vkopendoh.webapp.model;

import com.vkopendoh.webapp.util.DateUtil;
import com.vkopendoh.webapp.util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;

    private List<Experience> experiences = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, Experience... experiences) {
        this(new Link(name, url), Arrays.asList(experiences));
    }

    public Organization(Link homePage, List<Experience> experiences) {
        this.homePage = homePage;
        this.experiences = experiences;
    }

    public void setContent(List<Experience> content) {
        this.experiences = content;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setHomePage(Link homePage) {
        this.homePage = homePage;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                experiences.equals(that.experiences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, experiences);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Serializable {
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth startDate;

        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth endDate;

        private String title;

        private String description;

        public Experience() {
        }

        public Experience(YearMonth startDate, YearMonth endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate in Organization must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public Experience(int startYear, Month startMonth, String title, String description) {
            this(YearMonth.of(startYear, startMonth), DateUtil.NOW, title, description);
        }

        public Experience(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(YearMonth.of(startYear, startMonth), YearMonth.of(endYear, endMonth), title, description);
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public void setStartDate(YearMonth startDate) {
            this.startDate = startDate;
        }

        public YearMonth getEndDate() {
            return endDate;
        }

        public void setEndDate(YearMonth endDate) {
            this.endDate = endDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Experience that = (Experience) o;
            return startDate.equals(that.startDate) &&
                    endDate.equals(that.endDate) &&
                    title.equals(that.title) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }
    }
}
