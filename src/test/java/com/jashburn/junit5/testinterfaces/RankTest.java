package com.jashburn.junit5.testinterfaces;

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
