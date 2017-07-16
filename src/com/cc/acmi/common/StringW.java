/*
 * Copyright (c) 2003, Henri Yandell
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the 
 * following conditions are met:
 * 
 * + Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimer.
 * 
 * + Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimer in the documentation 
 *   and/or other materials provided with the distribution.
 * 
 * + Neither the name of Genjava-Core nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.cc.acmi.common;

import org.apache.commons.lang.StringUtils;


/**
 * A set of String library static methods. While extending String or 
 * StringBuffer would have been the nicest solution, that is not 
 * possible, so a simple set of static methods seems the most workable.
 *
 * Most methods have now gone to Commons Lang StringUtils.
 */
final public class StringW {

	static public String join(Object[] objs, String sep, String pre, String post) {
		String ret = StringUtils.join(objs, sep);
		if( (ret != null) && (ret != "")) {
			return pre + ret + post;
		} else {
			return ret;
		}
	}

    /**
     * Create a word-wrapped version of a String. Wrap at 80 characters and 
     * use newlines as the delimiter. If a word is over 80 characters long 
     * use a - sign to split it.
     */
    static public String wordWrap(String str) {
        return wordWrap(str, 80, "\n", "-", true);
    }
    /**
     * Create a word-wrapped version of a String. Wrap at a specified width and 
     * use newlines as the delimiter. If a word is over the width in lenght 
     * use a - sign to split it.
     */
    static public String wordWrap(String str, int width) {
        return wordWrap(str, width, "\n", "-", true);
    }
    /**
     * Word-wrap a string.
     *
     * @param str   String to word-wrap
     * @param width int to wrap at
     * @param delim String to use to separate lines
     * @param split String to use to split a word greater than width long
     *
     * @return String that has been word wrapped (with the delim inside width boundaries)
     */
  static public String wordWrap(String str, int width, String delim, String split ) {
    return wordWrap(str, width, delim, split, true);
  }
    /**
     * Word-wrap a string.
     *
     * @param str   String to word-wrap
     * @param width int to wrap at
     * @param delim String to use to separate lines
     * @param split String to use to split a word greater than width long
     * @param delimInside wheter or not delim should be included in chunk before length reaches width.
     *
     * @return String that has been word wrapped
     */
    static public String wordWrap(String str, int width, String delim,
                                  String split, boolean delimInside) {
        int sz = str.length();

        //        System.err.println( ">>>> inside: " + delimInside  + " sz : " + sz );

        /// shift width up one. mainly as it makes the logic easier
        width++;

        // our best guess as to an initial size
        StringBuffer buffer = new StringBuffer(sz/width*delim.length()+sz);

        // every line might include a delim on the end
        //        System.err.println( "width before: "+ width );
        if ( delimInside ) {
          width = width - delim.length();
        } else {
          width --;
        }
        //        System.err.println( "width after: "+ width );

        int idx = -1;
        String substr = null;

        // beware: i is rolled-back inside the loop
        for(int i=0; i<sz; i+=width) {

            // on the last line
            if(i >= sz - width) {
                buffer.append(str.substring(i));
//                System.err.print("LAST-LINE: "+str.substring(i));
                break;
            }

//            System.err.println("loop[i] is: "+i);
            // the current line
            substr = str.substring(i, i+width);
            //            System.err.println( "substr: " + substr );

            // is the delim already on the line
            idx = substr.indexOf(delim);
            //            System.err.println( "i: " + i + " idx : " + idx );
            if(idx != -1) {
                buffer.append(substr.substring(0,idx));
//                System.err.println("Substr: '"substr.substring(0,idx)+"'");
                buffer.append(delim);
                i -= width-idx-delim.length();
                
//                System.err.println("loop[i] is now: "+i);
//                System.err.println("ounfd-whitespace: '"+substr.charAt(idx+1)+"'.");
                // Erase a space after a delim. Is this too obscure?
                if(substr.length() > idx+1) {
                    if(substr.charAt(idx+1) != '\n') {
                        if(Character.isWhitespace(substr.charAt(idx+1))) {
                            i++;
                        }
                    }
                }
//                System.err.println("i -= "+width+"-"+idx);
                continue;
            }

            idx = -1;

            // figure out where the last space is
            char[] chrs = substr.toCharArray();
            for(int j=width; j>0; j--) {
                if(Character.isWhitespace(chrs[j-1])) {
                    idx = j;
//                    System.err.println("Found whitespace: "+idx);
                    break;
                }
            }

            // idx is the last whitespace on the line.
//            System.err.println("idx is "+idx);
            if(idx == -1) {
                for(int j=width; j>0; j--) {
                    if(chrs[j-1] == '-') {
                        idx = j;
//                        System.err.println("Found Dash: "+idx);
                        break;
                    }
                }
                if(idx == -1) {
                    buffer.append(substr);
                    buffer.append(delim);
//                    System.err.print(substr);
//                    System.err.print(delim);
                } else {
                    if(idx != width) {
                        idx++;
                    }
                    buffer.append(substr.substring(0,idx));
                    buffer.append(delim);
//                    System.err.print(substr.substring(0,idx));
//                    System.err.print(delim);
                    i -= width-idx;
                }
            } else {
                /*
                if(force) {
                    if(idx == width-1) {
                        buffer.append(substr);
                        buffer.append(delim);
                    } else {
                        // stick a split in.
                        int splitsz = split.length();
                        buffer.append(substr.substring(0,width-splitsz));
                        buffer.append(split);
                        buffer.append(delim);
                        i -= splitsz;
                    }
                } else {
                */
                    // insert spaces
                    buffer.append(substr.substring(0,idx));
                    buffer.append(StringUtils.repeat(" ",width-idx));
//                    System.err.print(substr.substring(0,idx));
//                    System.err.print(StringUtils.repeat(" ",width-idx));
                    buffer.append(delim);
//                    System.err.print(delim);
//                    System.err.println("i -= "+width+"-"+idx);
                    i -= width-idx;
//                }
            }
        }
//        System.err.println("\n*************");
        return buffer.toString();
    }

}
