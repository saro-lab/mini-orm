package me.saro.miniorm

class Util {
    companion object {

        @JvmStatic
        fun camelToUnderscore(text: String): String {
            val tmp = StringBuilder(text.length * 2)
            var upper = true
            for (i in text.indices) {
                val c = text[i]

                if (c.isUpperCase()) {
                    if (!upper) {
                        tmp.append('_')
                    }
                    tmp.append(c.lowercaseChar())
                    upper = true
                } else {
                    tmp.append(c)
                    upper = false
                }
            }
            return tmp.toString()
        }


        @JvmStatic
        fun aaaa() {



            println("aaaa")
        }



    }
}