package com.overbond;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.overbond.exceptions.NoArgumentsException;
import com.overbond.models.Data;
import com.overbond.models.Input;
import com.overbond.models.OutPutData;
import com.overbond.models.Output;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class Application {

    public static void main(String[] args) {
        try{
            if(args.length == 0 || args.length < 2) {
                throw new NoArgumentsException("Invalid Arguments are provided.");
            }
            FileReader fileReader = new FileReader(new File(Application.class
                    .getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent()+ "/" + args[0]);
            
            ObjectMapper objectMapper = new ObjectMapper();
            Input inputItem = objectMapper.readValue(fileReader, Input.class);
            
            Map<String, List<Data>> typeMap = inputItem.getDataList().stream()
                    .filter(item -> !(StringUtils.isEmpty(item.getId()) ||
                        StringUtils.isEmpty(item.getTenor()) || StringUtils.isEmpty(item.getType()) ||
                        StringUtils.isEmpty(item.getYield())))
                    .collect(groupingBy(Data::getType));
            
            Output output = new Output();
            
            for(Data corporateItem: typeMap.get("corporate")) {

                Float corporateTenor = Float.parseFloat(corporateItem.getTenor().split(" ")[0]);

                Float yearsOfMaturity = null;
                Data selectedGovtObj = null;

                for(Data govtItem : typeMap.get("government")) {

                    Float govtTenor = Float.parseFloat(govtItem.getTenor().split(" ")[0]);

                    if(selectedGovtObj == null) {

                        selectedGovtObj = govtItem;

                        if(govtTenor > corporateTenor) {
                            yearsOfMaturity = govtTenor - corporateTenor;
                        } else {
                            yearsOfMaturity = corporateTenor - govtTenor;
                        }

                    } else {
                        Float  newYearsOfMaturity = null;
                        if(govtTenor > corporateTenor) {
                            newYearsOfMaturity = govtTenor - corporateTenor;
                        } else {
                            newYearsOfMaturity = corporateTenor - govtTenor;
                        }

                        if(newYearsOfMaturity < yearsOfMaturity) {
                            yearsOfMaturity = newYearsOfMaturity;
                            selectedGovtObj = govtItem;
                        }

                        if (newYearsOfMaturity == yearsOfMaturity) {
                            if(selectedGovtObj.getAmountWithStanding() < govtItem.getAmountWithStanding()) {
                                yearsOfMaturity = newYearsOfMaturity;
                                selectedGovtObj = govtItem;
                            }
                        }
                    }
                }
                
                String spread = String.format("%.0f", (Float
                        .parseFloat(corporateItem.getYield().substring(0, corporateItem.getYield().length() - 1))
                        - Float.parseFloat(selectedGovtObj.getYield().substring(0, selectedGovtObj.getYield()
                                .length() - 1))) * 100);

                String finalSpread = spread + " bps";

                OutPutData outPutData = OutPutData.builder()
                                    .corporateBondId(corporateItem.getId())
                                    .governmentBondId(selectedGovtObj.getId())
                                    .spreadToBenchMark(finalSpread).build();

                output.getData().add(outPutData);
            }
            objectMapper.writeValue(new File(args[1]), output);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch( Exception ex) {
            ex.printStackTrace();
        }

    }

}
