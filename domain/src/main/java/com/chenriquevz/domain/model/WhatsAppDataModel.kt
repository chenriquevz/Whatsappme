package com.chenriquevz.domain.model

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE


@Entity
data class WhatsAppContact(@PrimaryKey val phoneNumber: String, val name: String? = null)
@Entity
data class Category(@PrimaryKey val categoryName: String) {

    //TODO make category have an ID (related to a colour(?) and name be optional
    object DefaultCategories {
        const val FAVORITE = "FAVORITE"
    }
}
@Entity
data class WhatsAppHistory(@PrimaryKey val timeStamp: Long, val phoneNumberHistory: String)

data class WhatsAppContactForDelete (val phoneNumber: String)
data class CategoryForDelete (val categoryName: String)
data class WhatsAppHistoryForDelete(val timeStamp: Long)
data class WhatsAppHistoryPhoneNumberForDelete(val phoneNumberHistory: String)


@Entity(
    primaryKeys = ["phoneNumber", "categoryName"],
    /* SUGGESTED */
    foreignKeys = [ForeignKey(
        entity = WhatsAppContact::class,
        parentColumns = ["phoneNumber"],
        childColumns = ["phoneNumber"],
        /* SUGGESTED with ForeignKey */
        onDelete = CASCADE,
        onUpdate = CASCADE
    ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryName"],
            childColumns = ["categoryName"],
            /* SUGGESTED with ForeignKey */
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class WhatsAppContactWithCategory(
    val phoneNumber: String,
    val categoryName: String
)

data class WhatsAppContactWithCategoryAndTimeStamp(
    @Embedded val whatsAppContact: WhatsAppContact,
    @Relation(
        parentColumn = "phoneNumber",
        entityColumn = "phoneNumberHistory"
    )
    val timeStamps: List<WhatsAppHistory>,

    @Relation(
        parentColumn = "phoneNumber",
        entityColumn = "categoryName",
        associateBy = Junction(WhatsAppContactWithCategory::class)
    )
    val categories: List<Category>
)


data class CategoryWithWhatsAppContacts(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "phoneNumber",
        associateBy = Junction(WhatsAppContactWithCategory::class)
    )
    val phoneNumbers: List<WhatsAppContact>
)

data class WhatsAppHistoryWithWhatsappContact(
    @Embedded val whatsAppHistory: WhatsAppHistory,
    @Relation(
        entity = WhatsAppContact::class,
        parentColumn = "phoneNumberHistory",
        entityColumn = "phoneNumber"
    )
    val whatsAppContactWithCategoryAndTime: WhatsAppContactWithCategoryAndTimeStamp,
)
