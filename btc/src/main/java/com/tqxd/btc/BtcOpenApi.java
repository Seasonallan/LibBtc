package com.tqxd.btc;


import com.quincysx.crypto.CoinTypes;
import com.quincysx.crypto.ECKeyPair;
import com.quincysx.crypto.bip32.ExtendedKey;
import com.quincysx.crypto.bip32.ValidationException;
import com.quincysx.crypto.bip39.MnemonicGenerator;
import com.quincysx.crypto.bip39.RandomSeed;
import com.quincysx.crypto.bip39.SeedCalculator;
import com.quincysx.crypto.bip39.WordCount;
import com.quincysx.crypto.bip39.wordlists.English;
import com.quincysx.crypto.bip44.AddressIndex;
import com.quincysx.crypto.bip44.BIP44;
import com.quincysx.crypto.bip44.CoinPairDerive;

import org.json.JSONObject;

import java.util.List;

/**
 * btc钱包相关API
 */
public class BtcOpenApi {

    /**
     * 钱包相关
     */
    public static class Wallet {

        /**
         * 通过助记词创建钱包
         *
         * @param list
         * @return
         */
        public static ECKeyPair createFromMnemonic(List<String> list) {
            byte[] seed = new SeedCalculator().calculateSeed(list, "");
            ExtendedKey extendedKey = null;
            ECKeyPair master = null;
            try {
                extendedKey = ExtendedKey.create(seed);
                AddressIndex address = BIP44.m().purpose44()
                        .coinType(CoinTypes.Bitcoin)
                        .account(0)
                        .external()
                        .address(0);
                CoinPairDerive coinKeyPair = new CoinPairDerive(extendedKey);
                master = coinKeyPair.derive(address);
                return master;
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 创建随机助记词
         *
         * @return
         */
        public static List<String> createRandomMnemonic() {
            byte[] random = RandomSeed.random(WordCount.TWELVE);
            return new MnemonicGenerator(English.INSTANCE).createMnemonic(random);
        }


    }


    /**
     * 请求相关
     */
    public static class Request {


        /**
         * 获取账户余额
         *
         * @return
         */
        public static boolean getAccountInfo(String address) {
            //address = "bc1qjvkwdrkmvhx9d07lg4fclvh2l0nnsy5nv5vu7k";
            try {
                String url = "https://tokenview.com/api/search/" + address;
                // String url = "https://services.tokenview.com/vipapi" + "/address/btc/" + address + "/1%2F1?&apikey=AnqHS6Rs2WX0hwFXlrv";
                //LogRipple.printForce(url);
                String res = SimpleRequest.getRequest(url);
                if (res != null) {
                    LogBtc.printForce(res);
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getInt("code") == 1) {
                        return true;
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            //LogRipple.printForce(address + "--:" + res);

            return false;
        }


    }

}
