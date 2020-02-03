package ch.tutteli.kbox

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.kbox.atrium.assert
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.Suite
import org.spekframework.spek2.style.specification.describe

object MapParentsSpec : Spek({

    fun Suite.testNonExistingChild(map: Map<String, String>) {
        context("passing non existing child") {
            it("returns an empty set") {
                val result = map.mapParents("a")
                assert(result).isEmpty()
            }
        }
    }

    describe("map parents") {

        context("empty map") {
            it("returns an empty set") {
                val result = mapOf<String, String>().mapParents("a")
                assert(result).isEmpty()
            }
        }
        val child = "child"
        val parent = "parent"
        val grandparent = "grandparent"

        context("one child parent relation") {
            val map = mapOf(child to parent)

            testNonExistingChild(map)

            context("passing child") {
                it("returns set with the parent") {
                    val result = map.mapParents(child)
                    assert(result).containsExactly(parent)
                }
            }

            context("passing parent") {
                it("returns an empty set") {
                    val result = map.mapParents(child)
                    assert(result).containsExactly(parent)
                }
            }
        }

        context("one child parent and one parent grandparent relation") {
            val map = mapOf(child to parent, parent to grandparent)

            testNonExistingChild(map)
            context("passing child") {
                it("returns set with the parent and grandparent") {
                    val result = map.mapParents(child)
                    assert(result).containsExactly(parent, grandparent)
                }
            }

            context("passing parent") {
                it("returns set with the grandparent") {
                    val result = map.mapParents(parent)
                    assert(result).containsExactly(grandparent)
                }
            }

            context("passing grandparent") {
                it("returns empty set") {
                    val result = map.mapParents(grandparent)
                    assert(result).isEmpty()
                }
            }
        }

        context("two child parent relations unrelated") {
            val map = mapOf(child to parent, grandparent to parent)

            testNonExistingChild(map)

            context("passing child 1") {
                it("returns set only with related parent") {
                    val result = map.mapParents(child)
                    assert(result).containsExactly(parent)
                }
            }

            context("passing child 2") {
                it("returns set only with related parent") {
                    val result = map.mapParents(grandparent)
                    assert(result).containsExactly(parent)
                }
            }
        }

        context("one child to child relation (cycle)") {
            val map = mapOf(child to child)

            context("nothing passed for failIfCyclic (= false)") {

                testNonExistingChild(map)

                context("passing child") {
                    it("returns an empty set") {
                        val result = map.mapParents(child)
                        assert(result).isEmpty()
                    }
                }
            }

            context("true passed for failIfCyclic") {

                testNonExistingChild(map)

                context("passing child") {
                    it("throws an IllegalStateException showing the cycle child -> child") {
                        assert {
                            map.mapParents(child, failIfCyclic = true)
                        }.toThrow<IllegalStateException> {
                            message { contains("$child -> $child") }
                        }
                    }
                }
            }
        }

        context("one child to parent and one parent to child relation (cycle)") {
            val map = mapOf(child to parent, parent to child)

            context("nothing passed for failIfCyclic (= false)") {

                testNonExistingChild(map)

                context("passing child") {
                    it("returns parent") {
                        val result = map.mapParents(child)
                        assert(result).containsExactly(parent)
                    }
                }

                context("passing parent") {
                    it("returns child") {
                        val result = map.mapParents(parent)
                        assert(result).containsExactly(child)
                    }
                }
            }

            context("true passed for failIfCyclic") {

                testNonExistingChild(map)

                context("passing child") {
                    it("throws an IllegalStateException showing the cycle child -> parent -> child") {
                        assert {
                            map.mapParents(child, failIfCyclic = true)
                        }.toThrow<IllegalStateException> {
                            message { contains("$child -> $parent -> $child") }
                        }
                    }
                }

                context("passing parent") {
                    it("throws an IllegalStateException showing the cycle parent -> child -> parent") {
                        assert {
                            map.mapParents(parent, failIfCyclic = true)
                        }.toThrow<IllegalStateException> {
                            message { contains("$parent -> $child -> $parent") }
                        }
                    }
                }
            }
        }

        context("one child to parent, parent to grandparent and one grandparent to child relation (cycle)") {
            val map = mapOf(child to parent, parent to grandparent, grandparent to child)

            context("nothing passed for failIfCyclic (= false)") {

                testNonExistingChild(map)

                context("passing child") {
                    it("returns parent and grandparent") {
                        val result = map.mapParents(child)
                        assert(result).containsExactly(parent, grandparent)
                    }
                }

                context("passing parent") {
                    it("returns grandparent and child") {
                        val result = map.mapParents(parent)
                        assert(result).containsExactly(grandparent, child)
                    }
                }

                context("passing grandparent") {
                    it("returns child and parent") {
                        val result = map.mapParents(grandparent)
                        assert(result).containsExactly(child, parent)
                    }
                }
            }

            context("true passed for failIfCyclic") {

                testNonExistingChild(map)

                context("passing child") {
                    it("throws an IllegalStateException showing the cycle child -> parent -> grandparent -> child") {
                        assert {
                            map.mapParents(child, failIfCyclic = true)
                        }.toThrow<IllegalStateException> {
                            message { contains("$child -> $parent -> $grandparent -> $child") }
                        }
                    }
                }

                context("passing parent") {
                    it("throws an IllegalStateException showing the cycle parent -> grandparent -> child -> parent") {
                        assert {
                            map.mapParents(parent, failIfCyclic = true)
                        }.toThrow<IllegalStateException> {
                            message { contains("$parent -> $grandparent -> $child -> $parent") }
                        }
                    }
                }

                context("passing grandparent") {
                    it("throws an IllegalStateException showing the cycle grandparent -> child -> parent -> grandparent") {
                        assert {
                            map.mapParents(grandparent, failIfCyclic = true)
                        }.toThrow<IllegalStateException> {
                            message { contains("$grandparent -> $child -> $parent -> $grandparent") }
                        }
                    }
                }
            }
        }
    }
})

