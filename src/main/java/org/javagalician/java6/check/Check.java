/*
 * =============================================================================
 * 
 *   Copyright (c) 2009, The JAVAGALICIAN team (http://www.javagalician.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.javagalician.java6.check;

import java.text.Collator;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import sun.util.resources.LocaleData;

public class Check {

    
    
    
    public static void main(String[] args) {
    
        try {

            
            
            System.out.println("==================================");
            System.out.println(" JAVAGALICIAN LOCALE CHECKS");
            System.out.println("==================================");

            
            System.out.println("Checking environment...");

            boolean vmVersionOK = true;
            try {
                Class.forName("java.util.spi.LocaleServiceProvider");
            } catch (Exception e) {
                vmVersionOK = false;
            }

            System.out.println("  > Checking Java version: [" + System.getProperty("java.version") + "]  " + (vmVersionOK? "[OK]" : "[FAIL]"));

            if (!vmVersionOK) {
                System.out.println("-----------------------------------------------------");
                System.out.println("BAD JAVA VERSION: JAVAGALICIAN NEEDS JAVA 6 OR HIGHER");
                System.out.println("-----------------------------------------------------");
                System.exit(1);
            }
            
            final Locale[] availableLocales = Locale.getAvailableLocales();
            System.out.println("Available locales are: " + Arrays.asList(availableLocales));
            
            
            final Locale gl = new Locale("gl");
            final Locale glES = new Locale("gl", "ES");
            final Locale[] locales = new Locale[] {gl, glES};
            final boolean[] contained = new boolean[locales.length];
            for (int i = 0; i < contained.length; i++) {
                contained[i] = false;
            }
            final boolean[] correct = new boolean[locales.length];
            for (int i = 0; i < correct.length; i++) {
                correct[i] = true;
            }
            
            for (int i = 0; i < locales.length; i++) {
                
                System.out.println();
                System.out.println("----------------------------------");
                System.out.println(" Checking: " + locales[i]);
                System.out.println("----------------------------------");
                
                System.out.println();
                
                for (int j = 0; j < availableLocales.length; j++) {
                    final Locale locale = availableLocales[j];
                    if (locales[i].getCountry() == null || locales[i].getCountry().equals("")) {
                        if ((locale.getCountry() == null || locale.getCountry().equals("")) && locales[i].getLanguage().equals(locale.getLanguage())) {
                            contained[i] = true;
                        }
                    } else {
                        if (locales[i].getCountry().equals(locale.getCountry()) && locales[i].getLanguage().equals(locale.getLanguage())) {
                            contained[i] = true;
                        }
                    }
                }
                
                System.out.println("Checking date formatting...");

                
                final DateFormat dateFormatFull = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locales[i]);
                final DateFormat dateFormatLong = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locales[i]);
                final DateFormat dateFormatMed = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locales[i]);
                final DateFormat dateFormatShort = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locales[i]);
                
                final Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 31);
                cal.set(Calendar.MONTH, 11);
                cal.set(Calendar.YEAR, 2009);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                
                final String glDateFull = dateFormatFull.format(cal.getTime());
                final String glDateLong = dateFormatLong.format(cal.getTime());
                final String glDateMed = dateFormatMed.format(cal.getTime());
                final String glDateShort = dateFormatShort.format(cal.getTime());
                
                final String fullDesiredFormat = "xoves 31 de decembro de 2009 23H59' CET";
                final boolean fullDateFormatOK = fullDesiredFormat.equals(glDateFull);
                
                final String longDesiredFormat = "31 de decembro de 2009 23:59:59 CET";
                final boolean longDateFormatOK = longDesiredFormat.equals(glDateLong);
                
                final String medDesiredFormat = "31-dec-2009 23:59:59";
                final boolean medDateFormatOK = medDesiredFormat.equals(glDateMed);
                
                final String shortDesiredFormat = "31/12/09 23:59";
                final boolean shortDateFormatOK = shortDesiredFormat.equals(glDateShort);
                
                
                correct[i] = correct[i] && fullDateFormatOK && longDateFormatOK && medDateFormatOK && shortDateFormatOK;
                
                
                System.out.println("  > Checking full date format: [" + glDateFull + "]  " + (fullDateFormatOK? "[OK]" : "[FAIL]"));
                System.out.println("  > Checking long date format: [" + glDateLong + "]  " + (longDateFormatOK? "[OK]" : "[FAIL]"));
                System.out.println("  > Checking medium date format: [" + glDateMed + "]  " + (medDateFormatOK? "[OK]" : "[FAIL]"));
                System.out.println("  > Checking short date format: [" + glDateShort + "]  " + (shortDateFormatOK? "[OK]" : "[FAIL]"));
                
                
                
                System.out.println("---");
                System.out.println("Checking number formatting...");

                final NumberFormat numberFormat = NumberFormat.getInstance(locales[i]);
                numberFormat.setMaximumFractionDigits(3);
                final NumberFormat numberCurrencyFormat = NumberFormat.getCurrencyInstance(locales[i]);
                
                final double n = 23124213.4563;
                final String nDesiredFormat = "23.124.213,456";
                final String nCurrencyDesiredFormat = "23.124.213,46 \u20AC";
                
                final String nFormat = numberFormat.format(n);
                final String nCurrencyFormat = numberCurrencyFormat.format(n);
                
                final boolean nFormatOK = nDesiredFormat.equals(nFormat);
                final boolean nCurrencyFormatOK = nCurrencyDesiredFormat.equals(nCurrencyFormat);

                correct[i] = correct[i] && nFormatOK && nCurrencyFormatOK;

                System.out.println("  > Checking decimal number format: [" + nFormat + "]  " + (nFormatOK? "[OK]" : "[FAIL]"));
                System.out.println("  > Checking currency format: [" + nCurrencyFormat + "]  " + (nCurrencyFormatOK? "[OK]" : "[FAIL]"));

                /*
                jdk1.7.0_79/src.zip!/java/text/DecimalFormatSymbols.java:547

                decimalSeparator = numberElements[0].charAt(0);
                groupingSeparator = numberElements[1].charAt(0);
                 */

                String[] numberElements = LocaleData.getNumberFormatData(locales[i]).getStringArray("NumberElements");
                System.out.println("decimalSeparator: "+numberElements[0].charAt(0));
                System.out.println("groupSeparator: "+numberElements[1].charAt(0));

                DecimalFormatSymbols symbols = new DecimalFormatSymbols(locales[i]);
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

                final String nFormat2 = decimalFormat.format(n);

                final boolean nFormatOK2 = nDesiredFormat.equals(nFormat2);

                correct[i] = correct[i] && nFormatOK2;

                System.out.println("  > Checking decimal number format: [" + nFormat2 + "]  " + (nFormatOK2? "[OK]" : "[FAIL]"));
                
                System.out.println("---");
                System.out.println("Checking collation...");
                
                final List words = Arrays.asList(new String[] {"\u00C1tono","\u00C1tomo", "at\u00F3nito", "at\u00F3mico"});
                final List desiredWords = Arrays.asList(new String[] {"at\u00F3mico", "\u00C1tomo", "at\u00F3nito", "\u00C1tono"});
                
                final Collator collator = Collator.getInstance(locales[i]);
                Collections.sort(words, collator);

                boolean collationOK = desiredWords.equals(words); 

                correct[i] = correct[i] && collationOK;
                
                System.out.println("  > Checking word order: " + words + "  " + (collationOK? "[OK]" : "[FAIL]"));

                
                
                System.out.println("---");
                System.out.println("Checking locale naming...");

                final Locale esAR = new Locale("es", "AR");
                final Locale deDE = new Locale("de", "DE");
                final Locale noNO = new Locale("no", "NO");
                
                final String country1 = esAR.getDisplayCountry(locales[i]);
                final String country2 = deDE.getDisplayCountry(locales[i]);
                final String country3 = noNO.getDisplayCountry(locales[i]);
                final List countries = Arrays.asList(new String[] { country1, country2, country3 });
                final List desCountries = Arrays.asList(new String[] { "Arxentina", "Alema\u00F1a", "Noruega" });
                final boolean countriesOK = countries.equals(desCountries);
                
                final String lang1 = esAR.getDisplayLanguage(locales[i]);
                final String lang2 = deDE.getDisplayLanguage(locales[i]);
                final String lang3 = noNO.getDisplayLanguage(locales[i]);
                final List langs = Arrays.asList(new String[] { lang1, lang2, lang3 });
                final List desLangs = Arrays.asList(new String[] { "castel\u00E1n", "alem\u00E1n", "noruegu\u00E9s" });
                final boolean langsOK = langs.equals(desLangs);

                correct[i] = correct[i] && countriesOK && langsOK;
                
                
                System.out.println("  > Checking country names: " + countries + "  " + (countriesOK? "[OK]" : "[FAIL]"));
                System.out.println("  > Checking language names: " + langs + "  " + (langsOK? "[OK]" : "[FAIL]"));
                
                
                System.out.println("---");
                System.out.println("Checking Time Zone naming...");
                
                final TimeZone tz = TimeZone.getTimeZone("CET");

                final String cetDaylightName = tz.getDisplayName(true, TimeZone.LONG, locales[i]);
                final String cetDaylightNameDesired = "Hora de ver\u00E1n de Europa Central";
                
                final boolean timeZoneOK = cetDaylightNameDesired.equals(cetDaylightName);

                correct[i] = correct[i] && timeZoneOK;
                
                System.out.println("  > Checking timezone names: [" + cetDaylightName + "]  " + (timeZoneOK? "[OK]" : "[FAIL]"));

            }
            

            System.out.println();
            System.out.println();
            for (int i = 0; i < locales.length; i++) {
                if (contained[i]) {
                    if (correct[i]) {
                        System.out.println("---------------------------------------------------");
                        System.out.println("GALICIAN LOCALE " + locales[i] + " AVAILABLE AND CORRECT");
                        System.out.println("---------------------------------------------------");
                    } else {
                        System.out.println("---------------------------------------------------");
                        System.out.println("GALICIAN LOCALE " + locales[i] + " AVAILABLE BUT NOT CORRECT!!");
                        System.out.println("---------------------------------------------------");
                    }
                } else {
                    System.out.println("---------------------------------------------------");
                    System.out.println("GALICIAN LOCALE " + locales[i] + " *NOT* AVAILABLE");
                    System.out.println("---------------------------------------------------");
                }
            }
            
            
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
        
    }
    
}
