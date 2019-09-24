package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitPickerDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.interfaces.Removeable;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
public class FruitPickerService implements Removeable {

    private FruitPickerDAO fruitPickerDAO;
    private FruitDeliveryService fruitDeliveryService;
    private FruitTypeService fruitTypeService;

    @Autowired
    public FruitPickerService(FruitPickerDAO fruitPickerDAO, FruitDeliveryService fruitDeliveryService,
                              FruitTypeService fruitTypeService) {
        this.fruitPickerDAO = fruitPickerDAO;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitTypeService = fruitTypeService;
    }

    public void addFruitPicker(FruitPicker fruitPicker) {
        fruitPickerDAO.save(fruitPicker);
    }

    public List<FruitPicker> findAll() {
        List<FruitPicker> pickersList = fruitPickerDAO.findAll();
        setOtherData(pickersList);
        setTypeInfo(pickersList);
        return pickersList;
    }

    @Cacheable(cacheNames = "findAllFruitPickers")
    public List<FruitPicker> findAll(String filter) {
        List<FruitPicker> pickersList = fruitPickerDAO.findAll();
        Iterator<FruitPicker> iterator = pickersList.iterator();
        setTypeInfo(pickersList);

        while (iterator.hasNext()) {
            FruitPicker fp = iterator.next();
            if (!Long.toString(fp.getId()).contains(filter) && !fp.getName().contains(filter) && !fp.getLastName().contains(filter)) {
                iterator.remove();
            }
        }

        setOtherData(pickersList);
        return pickersList;
    }

    public long getTotalAmountOfPickers() {
        return findAll().size();
    }

    public void removeAll() {
        fruitPickerDAO.deleteAll();
    }

    @Override
    public void removeById(Long id) {
        fruitPickerDAO.deleteById(id);
    }

    private List<FruitPicker> setTypeInfo(List<FruitPicker> fruitPickers) {

        for (FruitPicker fp : fruitPickers) {
            long packagesWithTypeOne = fruitDeliveryService.findByIdWithType(fp.getId(),
                    fruitTypeService.getType(0).getName()).stream().mapToLong(FruitDelivery::getPackageAmount).sum();
            long packagesWithTypeTwo = fruitDeliveryService.findByIdWithType(fp.getId(),
                    fruitTypeService.getType(1).getName()).stream().mapToLong(FruitDelivery::getPackageAmount).sum();

            long packagesWithTypeThree = fruitDeliveryService.findByIdWithType(fp.getId(),
                    fruitTypeService.getType(2).getName()).stream().mapToLong(FruitDelivery::getPackageAmount).sum();

            long packagesWithTypeFour = fruitDeliveryService.findByIdWithType(fp.getId(),
                    fruitTypeService.getType(3).getName()).stream().mapToLong(FruitDelivery::getPackageAmount).sum();

            fp.setPackageDeliveryWithTypeOne(packagesWithTypeOne);
            fp.setPackageDeliveryWithTypeTwo(packagesWithTypeTwo);
            fp.setPackageDeliveryWithTypeThree(packagesWithTypeThree);
            fp.setPackageDeliveryWithTypeFour(packagesWithTypeFour);

            // weight info
            BigDecimal weightWithTypeOne = fruitDeliveryService.findByIdWithType(fp.getId(),
                    fruitTypeService.getType(0).getName()).stream().map(FruitDelivery::getFruitWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal weightWithTypeTwo = fruitDeliveryService.findByIdWithType(fp.getId(),
                    fruitTypeService.getType(1).getName()).stream().map(FruitDelivery::getFruitWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal weightWithTypeThree = fruitDeliveryService.findByIdWithType(fp.getId(),
                    fruitTypeService.getType(2).getName()).stream().map(FruitDelivery::getFruitWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal weightWithTypeFour = fruitDeliveryService.findByIdWithType(fp.getId(),
                    fruitTypeService.getType(3).getName()).stream().map(FruitDelivery::getFruitWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

            fp.setWeightWithTypeOne(weightWithTypeOne);
            fp.setWeightWithTypeTwo(weightWithTypeTwo);
            fp.setWeightWithTypeThree(weightWithTypeThree);
            fp.setWeightWithTypeFour(weightWithTypeFour);
        }
        return fruitPickers;
    }

    private List<FruitPicker> setOtherData(List<FruitPicker> pickersList) {
        for (FruitPicker fp : pickersList) {
            if (fruitDeliveryService.getDeliveriesByPickerId(fp.getId()) == null) {
                fp.setPackageDeliveryAmount(0);
            } else {
                List<FruitDelivery> fruitDeliveries = fruitDeliveryService.getDeliveriesByPickerId(fp.getId());
                Long sum = 0L;
                for (FruitDelivery fd : fruitDeliveries) {
                    sum += fd.getPackageAmount();
                }
                fp.setPackageDeliveryAmount(sum);

            }
        }
        for (FruitPicker fp : pickersList) {
            if (fruitDeliveryService.getDeliveriesByPickerId(fp.getId()) == null) {
                fp.setPackageDeliveryAmount(0);
            } else {
                List<FruitDelivery> fruitDeliveries = fruitDeliveryService.getDeliveriesByPickerId(fp.getId());
                BigDecimal sum = new BigDecimal(0);
                for (FruitDelivery fd : fruitDeliveries) {
                    sum = sum.add(fd.getFruitWeight());
                }
                fp.setWeightSum(sum);

            }
        }
        return pickersList;
    }

}
