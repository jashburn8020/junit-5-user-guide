package com.jashburn.junit5.testinterfaces;

enum Rank {
    PRIVATE, LANCE_CORPORAL, CORPORAL, SERGEANT, STAFF_SERGEANT;
}


class RankTest implements EqualsContract<Rank>, ComparableContract<Rank> {

    @Override
    public Rank createValue() {
        return Rank.CORPORAL;
    }

    @Override
    public Rank createSmallerValue() {
        return Rank.PRIVATE;
    }

    @Override
    public Rank createNotEqualValue() {
        return Rank.STAFF_SERGEANT;
    }


}
