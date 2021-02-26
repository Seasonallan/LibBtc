package com.library.aitd;

import com.example.LogRipple;
import com.quincysx.crypto.BtcOpenApi;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户接口（注册登录） 单元测试
 */
public class ApiWallet_UnitTest extends BaseUnitTest {


    @Override
    protected void init() {
        LogRipple.enableLog(false);
        File file = new File(path);
        if (!file.exists()) {
            LogRipple.print("创建缓存用户文件=" + file.toString());
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    int threadCount = 50;//同时执行的线程数量

    List<RegisterThread> threadList = new ArrayList<>();

    @Override
    protected void proceed() {
        LogRipple.print("开启注册机数量=" + threadCount);

        for (int i = 0; i < threadCount; i++) {
            RegisterThread pickThread = new RegisterThread(i + 1);
            threadList.add(pickThread);
        }
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).proceed();
        }
    }


    public static class RegisterThread {

        private int id;

        RegisterThread(int id) {
            this.id = id;
        }

        int count = 0;

        protected void proceed() {
            LogRipple.print("线程" + id + ">>proceed>> 数据：" + count);
            if (count % 50 == 0) {
                LogRipple.printForce("线程" + id + ">>proceed>> 数据：" + count);
            }
            count ++;
            accountCheck();
        }

        private void accountCheck() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        byte[] random = RandomSeed.random(WordCount.TWELVE);
                        List<String> strings = new MnemonicGenerator(English.INSTANCE).createMnemonic(random);
                        ECKeyPair master = BtcOpenApi.Wallet.createFromMnemonic(strings);
                        if (master != null) {
                            if (BtcOpenApi.Request.getAccountInfo(master.getAddress())) {
                                File file = new File(path);
                                StringBuilder stringBuilder = new StringBuilder();
                                for (String str : strings) {
                                    stringBuilder.append(str).append(" ");
                                }
                                stringBuilder.append(master.getPrivateKey()).append("\n");

                                FileWriter fileWriter = null;
                                try {
                                    fileWriter = new FileWriter(file, true);
                                    fileWriter.write(stringBuilder.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (fileWriter != null) {
                                            fileWriter.close();
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            } else {
                                //LogRipple.print(seed + "--:" + xrpAccount.error_message);
                            }
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    proceed();
                }
            }).start();
        }


    }

}