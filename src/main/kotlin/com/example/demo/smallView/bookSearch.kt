package com.example.demo.smallView

import com.example.demo.controller.TabController
import com.example.demo.modal.SVG_SEARCH
import com.example.demo.myButton
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import com.jfoenix.effects.JFXDepthManager
import com.jfoenix.svg.SVGGlyph
import com.jfoenix.validation.RequiredFieldValidator
import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import tornadofx.*
import java.util.concurrent.Callable

/**
 * 图书检索
 */
fun bookSearch(): StackPane  =  StackPane().apply cd@{
        this.setOnKeyPressed {
            if (it.code == KeyCode.ENTER) {
                if (find(TabController::class).searchName.value.isNotBlank()) find(TabController::class).searchBook()
            }
        }
        prefWidth = 150.0
        fitToParentHeight()
        JFXDepthManager.setDepth(this, 1)
        val header = StackPane().apply {
            vbox {
                alignment = Pos.CENTER
                label("图书馆查询·外网版"){
                    textFill = Color.WHITE

                }
                label {
                    textFill = Color.WHITE
                    text = "图书数据不完整，更多请使用校内版！"
                }
                flowpane {
                    hgap = 5.0
                    vgap = 5.0
                    paddingAll = 10.0
                    //spacing = 10.0
                    this += myButton("设计")
                    this += myButton("营销")
                    this += myButton("公司")
                    this += myButton("商业")
                    this += myButton("机械")
                    this += myButton("高数")

                    this += myButton("JAVA")
                    this += myButton("Cad")
                    this += myButton("Android")
                    this += myButton("Max")
                    this += myButton("Python")
                    this += myButton("PhotoShop")

                }
            }
            style {
                backgroundColor += c("#4d4d4d")
                VBox.setVgrow(this@apply, Priority.ALWAYS)
            }
        }
        val body = StackPane().apply {
            minHeight = 100.0
            //搜索框
            val validationField = JFXTextField().apply {
                StackPane.setMargin(this, Insets(20.0))
                StackPane.setAlignment(this, Pos.CENTER)
                promptText = "输入书名:回车键快速搜索"
                //双向绑定
                this.bind(find(TabController::class).searchName)

            }

            val validator = RequiredFieldValidator()
            validator.message = "不可以为空哦"
            validationField.validators.add(validator)
            validationField.focusedProperty().addListener { _, _, newVal ->
                if (!newVal) {
                    validationField.validate()
                }
            }
            //添加搜索框
            add(validationField)
            style {
                backgroundColor += c("#fff")
            }
        }
        val sendBtn = JFXButton("").apply {
            action {
                if (find(TabController::class).searchName.value.isNullOrBlank()) return@action
                println("开始搜索:")
                println("当前searchName1：${find(TabController::class).searchName}")
                find(TabController::class).searchBook()
            }
            buttonType = JFXButton.ButtonType.RAISED
            rotate = -180.0
            setPrefSize(50.0, 50.0)
            style {
                backgroundRadius += CssBox(50.px, 50.px, 50.px, 50.px)
                backgroundColor += c("#4d4d4d")
            }
            graphic = SVGGlyph(-1, "search", SVG_SEARCH, Color.WHITE).apply {
                this.setPrefSize(40.0, 40.0)
            }
            //设置fab按钮始终固定在中间
            translateYProperty().bind(Bindings.createDoubleBinding(Callable { header.boundsInParent.height - this.height / 2 }, header.boundsInParentProperty(), this.heightProperty()))
            ripplerFill = c("#4d4d4d")
            StackPane.setMargin(this, Insets(0.0, 40.0, 0.0, 0.0))
            StackPane.setAlignment(this, Pos.TOP_RIGHT)
        }
        val content = VBox().apply {
            children.addAll(header, body)
        }
        //用add与 this += 是一样的
        add(content)
        add(sendBtn)


    }
