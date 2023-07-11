package com.example.demo.service;

import com.example.demo.model.Locations;
import com.example.demo.repository.DevicesRepository;
import com.example.demo.repository.LocationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    final DevicesRepository devicesRepository;
    final LocationsRepository locationsRepository;

    public List<Long> checkLagKCK() {
        List<Long> devicesKCK = new ArrayList<>();

        //KCK
        devicesKCK.add(9546L); // О 158 РМ 124 862531044328681
        devicesKCK.add(8115L); // С 641 ОН 124 868183037457681
        devicesKCK.add(8127L); // У 811 СС 124 868183037451718
        devicesKCK.add(10295L); // А 926 НТ 124 918738 -
        devicesKCK.add(10271L); // М 209 НТ 124 918819
        devicesKCK.add(7950L); // У 753 ОО 124 918735
        devicesKCK.add(10294L); // Н 734 ВТ 124 918734

        long lag = 0;
        long totalDevices = 0;

        for (Long device : devicesKCK) {
            List<Locations> locationsList = locationsRepository.find(device);

            if (locationsList.size() > 0) {
                Locations locations = locationsList.get(0);

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime getDeviceTime = locations.getDeviceTime().plusHours(7);
                LocalDateTime getInsertTime = locations.getInsertTime().plusHours(7);

                if (ChronoUnit.SECONDS.between(getDeviceTime, now) < 3600) {
                    long diff = ChronoUnit.SECONDS.between(getDeviceTime, getInsertTime);
                    lag = lag + diff;
                    totalDevices = totalDevices + 1;
                }
            }
        }

        devicesKCK.clear();
        devicesKCK.add(lag);
        devicesKCK.add(totalDevices);

        return devicesKCK;
    }

    public List<Long> checkLagKGT() {
        List<Long> devicesKGT = new ArrayList<>();

        //KGT
        devicesKGT.add(11365L); // А 918 ТЕ 124 194912477
        devicesKGT.add(7034L); // АЕ 424 24 194016412
        devicesKGT.add(10461L); // В 418 НН 124 242040666
        devicesKGT.add(8425L); // К 866 КТ 124 242118520
        devicesKGT.add(10333L); // В 315 РУ 47 242040704
        devicesKGT.add(6077L); // В 373 НН 124 41062
        devicesKGT.add(4740L); // С 822 ОУ 124 114726
        devicesKGT.add(6080L); // Р 223 НУ 124 41059

        long lag = 0;
        long totalDevices = 0;

        for (Long device : devicesKGT) {
            List<Locations> locationsList = locationsRepository.find(device);

            if (locationsList.size() > 0) {
                Locations locations = locationsList.get(0);

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime getDeviceTime = locations.getDeviceTime().plusHours(7);
                LocalDateTime getInsertTime = locations.getInsertTime().plusHours(7);

                if (ChronoUnit.SECONDS.between(getDeviceTime, now) < 3600) {
                    long diff = ChronoUnit.SECONDS.between(getDeviceTime, getInsertTime);
                    lag = lag + diff;
                    totalDevices = totalDevices + 1;
                }
            }
        }

        devicesKGT.clear();
        devicesKGT.add(lag);
        devicesKGT.add(totalDevices);

        return devicesKGT;
    }

    public List<Long> checkLagM2M() {
        List<Long> devicesM2M = new ArrayList<>();

        //M2M
        devicesM2M.add(11015L); // Н 028 НК 124 242035444
        devicesM2M.add(10409L); // А 950 ОХ 124 242042387
        devicesM2M.add(9103L); // О 406 РС 124 242126311
        devicesM2M.add(9225L); // У 010 КК 124 RUS 352353085596382
        devicesM2M.add(5755L); // У 542 НУ 124 RUS 353612087296294
        devicesM2M.add(9521L); // АТ 582 24 RUS 862531048020797
        devicesM2M.add(8339L); // Е 034 СЕ 124 RUS 866795038139877
        devicesM2M.add(8574L); // Н 772 КУ 124 RUS 1388381

        long lag = 0;
        long totalDevices = 0;

        for (Long device : devicesM2M) {
            List<Locations> locationsList = locationsRepository.find(device);

            if (locationsList.size() > 0) {
                Locations locations = locationsList.get(0);

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime getDeviceTime = locations.getDeviceTime().plusHours(7);
                LocalDateTime getInsertTime = locations.getInsertTime().plusHours(7);

                if (ChronoUnit.SECONDS.between(getDeviceTime, now) < 3600) {
                    long diff = ChronoUnit.SECONDS.between(getDeviceTime, getInsertTime);
                    lag = lag + diff;
                    totalDevices = totalDevices + 1;
                }
            }
        }

        devicesM2M.clear();
        devicesM2M.add(lag);
        devicesM2M.add(totalDevices);

        return devicesM2M;
    }
}