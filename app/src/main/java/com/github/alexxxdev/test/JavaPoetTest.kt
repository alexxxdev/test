package com.github.alexxxdev.test

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.MethodSpec
import javax.lang.model.element.Modifier


class JavaPoetTest {

    init {
        val main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(Void.TYPE)
                .addParameter(Array<String>::class.java, "args")
                .addStatement("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
                .build()

        val helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build()

        val javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build()

        javaFile.writeTo(System.out)
    }

    private fun whatsMyName(name: String): MethodSpec {
        return MethodSpec.methodBuilder(name)
                .returns(String::class.java)
                .addStatement("return \$S", name)
                .build()
    }
}