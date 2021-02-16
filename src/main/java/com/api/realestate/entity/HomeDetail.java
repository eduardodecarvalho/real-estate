package com.api.realestate.entity;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HOME")
@SQLDelete(sql = "UPDATE HOME SET DELETED = 1 WHERE ID = ?")
public class HomeDetails {
}
